package nyc.servlet;


import nyc.dal.*;
import nyc.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/find")
public class Find extends HttpServlet {

    protected AirBNBDAO airBNBDAO;
    protected BusinessDAO businessDAO;
    protected CollisionDAO collisionDAO;
    protected DestinationDAO destinationDAO;
    protected PointOfInterestDAO pointOfInterestDAO;
    protected EmergencyResponseDAO emergencyResponseDAO;
    protected GardenDAO gardenDAO;
    protected GraffitiDAO graffitiDAO;
    protected MarketDAO marketDAO;
    protected ParkDAO parkDAO;
    protected TripDAO tripDAO;
    protected UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        pointOfInterestDAO = PointOfInterestDAO.getInstance();
        airBNBDAO = AirBNBDAO.getInstance();
        businessDAO = BusinessDAO.getInstance();
        collisionDAO = CollisionDAO.getInstance();
        destinationDAO = DestinationDAO.getInstance();
        emergencyResponseDAO = EmergencyResponseDAO.getInstance();
        gardenDAO = GardenDAO.getInstance();
        graffitiDAO = GraffitiDAO.getInstance();
        marketDAO = MarketDAO.getInstance();
        parkDAO = ParkDAO.getInstance();
        tripDAO = TripDAO.getInstance();
        userDAO = UserDAO.getInstance();

    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String databaseObject = req.getParameter("object");
        if (databaseObject == null || databaseObject.trim().isEmpty()) {
            messages.put("success", "InvalidObject");
        } else {
            switch (databaseObject) {
                case "airbnb":
                    req.getRequestDispatcher("/AirBNBFind.jsp").forward(req, resp);
                    break;
                case "business":
                    req.getRequestDispatcher("/BusinessFind.jsp").forward(req, resp);
                    break;
                case "garden":
                    req.getRequestDispatcher("/GardenFind.jsp").forward(req, resp);
                    break;
                case "market":
                    req.getRequestDispatcher("/MarketFind.jsp").forward(req, resp);
                    break;
                case "park":
                    req.getRequestDispatcher("/ParkFind.jsp").forward(req, resp);
                    break;
                case "point_of_interest":
                    req.getRequestDispatcher("/PointOfInterestFind.jsp").forward(req, resp);
                    break;
                case "collision":
                    req.getRequestDispatcher("/CollisionFind.jsp").forward(req, resp);
                    break;
                case "emergency_response":
                    req.getRequestDispatcher("/EmergencyResponseFind.jsp").forward(req, resp);
                    break;
                case "graffiti":
                    req.getRequestDispatcher("/GraffitiFind.jsp").forward(req, resp);
                    break;
                default:
                    req.getRequestDispatcher("/404.jsp").forward(req, resp);
            }

        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String databaseObject = req.getParameter("object");
        if (databaseObject == null || databaseObject.trim().isEmpty()) {
            messages.put("success", "InvalidObject");
        } else {
            switch (databaseObject.toLowerCase()) {
                case "airbnb":
                    AirBNB airBNB = FindAirBNB(req);
                    messages.put("success", "Found AirBNB: " + airBNB.getKey());
                    req.setAttribute("airbnb", airBNB);
                    break;
                case "business":
                    Business business = FindBusiness(req);
                    messages.put("success", "Found Business: " + business.getKey());
                    req.setAttribute("business", business);
                    break;
                case "garden":
                    Garden garden = FindGarden(req);
                    messages.put("success", "Found Garden: " + garden.getKey());
                    req.setAttribute("garden", garden);
                    break;
                case "market":
                    Market market = FindMarket(req);
                    messages.put("success", "Found Market: " + market.getKey());
                    req.setAttribute("market", market);
                    break;
                case "park":
                    Park park = FindPark(req);
                    messages.put("success", "Found Park: " + park.getKey());
                    req.setAttribute("park", park);
                    break;
                case "point_of_interest":
                    String name = req.getParameter("name");
                    if (name == null || name.trim().isEmpty()) {
                        PointOfInterest point = FindPointOfInterest(req);
                        messages.put("success", "Found Point Of Interest: " + point.getKey());
                        req.setAttribute("point_of_interest", point);
                    } else {
                        //find by name
                        List<PointOfInterest> points = FindPointsOfInterest(name);
                    }

                    break;
                case "collision":
                    Collision collision = FindCollision(req);
                    messages.put("success", "Found Collision: " + collision.getKey());
                    req.setAttribute("collision", collision);
                    break;
                case "emergency_response":
                    EmergencyResponse emergency_response = FindEmergencyResponse(req);
                    messages.put("success", "Found Emergency Response: " + emergency_response.getKey());
                    req.setAttribute("emergency_response", emergency_response);
                    break;
                case "graffiti":
                    Graffiti graffiti = FindGraffiti(req);
                    messages.put("success", "Found Graffiti: " + graffiti.getKey());
                    req.setAttribute("graffiti", graffiti);
                    break;
                default:
                    req.getRequestDispatcher("/404.jsp").forward(req, resp);
            }

        }
        //TODO check this? -> should map the to file names?
        req.getRequestDispatcher("/Find?object=" + databaseObject + ".jsp").forward(req, resp);

    }

    private long fixLong(String input) throws IOException {
        long lngValue = 0;
        if (input == null || input.trim().isEmpty()) {
            return lngValue;
        } else {
            try {
                lngValue = Long.parseLong(input);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        return lngValue;
    }

    private List<PointOfInterest> FindPointsOfInterest(String name) throws IOException {
        try {
            return pointOfInterestDAO.getPointsOfInterestByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }
    private PointOfInterest FindPointOfInterest(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            return pointOfInterestDAO.getPointOfInterestByPointOfInterestID(fixLong(id));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    private AirBNB FindAirBNB(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            return airBNBDAO.getAirbnbByAirbnbID(fixLong(id));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    private Business FindBusiness(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            return businessDAO.getBusinessByBusinessID(fixLong(id));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    private Collision FindCollision(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            return collisionDAO.getCollisionByID(fixLong(id));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    private EmergencyResponse FindEmergencyResponse(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            return emergencyResponseDAO.getEmergencyResponseByID(fixLong(id));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    private Garden FindGarden(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            return gardenDAO.getGardenByGardenID(fixLong(id));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    private Graffiti FindGraffiti(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            return graffitiDAO.getGraffitiByID(fixLong(id));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    private Market FindMarket(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            return marketDAO.getMarketByMarketID(fixLong(id));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    private Park FindPark(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            return parkDAO.getParkByParkID(fixLong(id));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

}
