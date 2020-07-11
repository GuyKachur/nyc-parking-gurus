package nyc.servlet;


import nyc.dal.*;
import nyc.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/delete")
public class Delete extends HttpServlet {

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
                    req.getRequestDispatcher("/AirBNBDelete.jsp").forward(req, resp);
                    break;
                case "business":
                    req.getRequestDispatcher("/BusinessDelete.jsp").forward(req, resp);
                    break;
                case "garden":
                    req.getRequestDispatcher("/GardenDelete.jsp").forward(req, resp);
                    break;
                case "market":
                    req.getRequestDispatcher("/MarketDelete.jsp").forward(req, resp);
                    break;
                case "park":
                    req.getRequestDispatcher("/ParkDelete.jsp").forward(req, resp);
                    break;
                case "point_of_interest":
                    req.getRequestDispatcher("/PointOfInterestDelete.jsp").forward(req, resp);
                    break;
                case "collision":
                    req.getRequestDispatcher("/CollisionDelete.jsp").forward(req, resp);
                    break;
                case "emergency_response":
                    req.getRequestDispatcher("/EmergencyResponseDelete.jsp").forward(req, resp);
                    break;
                case "graffiti":
                    req.getRequestDispatcher("/GraffitiDelete.jsp").forward(req, resp);
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
                    if (DeleteAirBNB(req)) {
                        messages.put("success", "Deleted AirBNB");
                    }
                    break;
                case "business":
                    if (DeleteBusiness(req)) {
                        messages.put("success", "Deleted Business");
                    }
                    break;
                case "garden":
                    if (DeleteGarden(req)) {
                        messages.put("success", "Deleted Garden");
                    }
                    break;
                case "market":
                    if (DeleteMarket(req)) {
                        messages.put("success", "Deleted Market");
                    }
                    break;
                case "park":
                    if (DeletePark(req)) {
                        messages.put("success", "Deleted Market");
                    }
                    break;
                case "point_of_interest":
                    if (DeletePointOfInterest(req)) {
                        messages.put("success", "Deleted Point Of Interest");
                    }
                    break;
                case "collision":
                    if (DeleteCollision(req)) {
                        messages.put("success", "Deleted Collision");
                    }
                    break;
                case "emergency_response":
                    if (DeleteEmergencyResponse(req)) {
                        messages.put("success", "Deleted Emergency Response");
                    }
                    break;
                case "graffiti":
                    if (DeleteGraffiti(req)) {
                        messages.put("success", "Deleted Graffiti");
                    }
                    break;
                default:
                    req.getRequestDispatcher("/404.jsp").forward(req, resp);
            }

        }
        //TODO check this? -> should map the to file names?
        // or return to homepage?
        req.getRequestDispatcher("/Delete?object=" + databaseObject + ".jsp").forward(req, resp);

    }

    private float fixFloat(String input) throws IOException {
        float floatValue = 0.0F;
        if (input == null || input.trim().isEmpty()) {
            return floatValue;
        } else {
            try {
                floatValue = Float.parseFloat(input);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        return floatValue;
    }

    private int fixInt(String input) throws IOException {
        int intValue = 0;
        if (input == null || input.trim().isEmpty()) {
            return intValue;
        } else {
            try {
                intValue = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        return intValue;
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

    private Date fixDate(String input) throws IOException {
        // dob must be in the format yyyy-mm-dd.
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = dateFormat.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return date;
    }

    private boolean DeletePointOfInterest(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            PointOfInterest point = pointOfInterestDAO.getPointOfInterestByPointOfInterestID(fixLong(id));
            pointOfInterestDAO.delete(point);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean DeleteAirBNB(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            AirBNB airBNB = airBNBDAO.getAirbnbByAirbnbID(fixLong(id));
            airBNBDAO.delete(airBNB);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean DeleteBusiness(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            Business business = businessDAO.getBusinessByBusinessID(fixLong(id));
            businessDAO.delete(business);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean DeleteCollision(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            Collision collision = collisionDAO.getCollisionByID(fixLong(id));
            collisionDAO.delete(collision);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean DeleteEmergencyResponse(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            EmergencyResponse er = emergencyResponseDAO.getEmergencyResponseByID(fixLong(id));
            emergencyResponseDAO.delete(er);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean DeleteGarden(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            Garden garden = gardenDAO.getGardenByGardenID(fixLong(id));
            gardenDAO.delete(garden);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean DeleteGraffiti(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            Graffiti graffiti = graffitiDAO.getGraffitiByID(fixLong(id));
            graffitiDAO.delete(graffiti);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean DeleteMarket(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            Market market = marketDAO.getMarketByMarketID(fixLong(id));
            marketDAO.delete(market);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean DeletePark(HttpServletRequest req) throws IOException {
        String id = req.getParameter("id");
        try {
            Park park = parkDAO.getParkByParkID(fixLong(id));
            parkDAO.delete(park);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

}
