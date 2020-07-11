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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/create")
public class Create extends HttpServlet {

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
                    req.getRequestDispatcher("/AirBNBCreate.jsp").forward(req, resp);
                    break;
                case "business":
                    req.getRequestDispatcher("/BusinessCreate.jsp").forward(req, resp);
                    break;
                case "garden":
                    req.getRequestDispatcher("/GardenCreate.jsp").forward(req, resp);
                    break;
                case "market":
                    req.getRequestDispatcher("/MarketCreate.jsp").forward(req, resp);
                    break;
                case "park":
                    req.getRequestDispatcher("/ParkCreate.jsp").forward(req, resp);
                    break;
                case "point_of_interest":
                    req.getRequestDispatcher("/PointOfInterestCreate.jsp").forward(req, resp);
                    break;
                case "collision":
                    req.getRequestDispatcher("/CollisionCreate.jsp").forward(req, resp);
                    break;
                case "emergency_response":
                    req.getRequestDispatcher("/EmergencyResponseCreate.jsp").forward(req, resp);
                    break;
                case "graffiti":
                    req.getRequestDispatcher("/GraffitiCreate.jsp").forward(req, resp);
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
                    if (createAirBNB(req)) {
                        messages.put("success", "Created AirBNB");
                    }
                    break;
                case "business":
                    if (createBusiness(req)) {
                        messages.put("success", "Created Business");
                    }
                    break;
                case "garden":
                    if (createGarden(req)) {
                        messages.put("success", "Created Garden");
                    }
                    break;
                case "market":
                    if (createMarket(req)) {
                        messages.put("success", "Created Market");
                    }
                    break;
                case "park":
                    if (createPark(req)) {
                        messages.put("success", "Created Market");
                    }
                    break;
                case "point_of_interest":
                    if (createPointOfInterest(req)) {
                        messages.put("success", "Created Point Of Interest");
                    }
                    break;
                case "collision":
                    if (createCollision(req)) {
                        messages.put("success", "Created Collision");
                    }
                    break;
                case "emergency_response":
                    if (createEmergencyResponse(req)) {
                        messages.put("success", "Created Emergency Response");
                    }
                    break;
                case "graffiti":
                    if (createGraffiti(req)) {
                        messages.put("success", "Created Graffiti");
                    }
                    break;
                default:
                    req.getRequestDispatcher("/404.jsp").forward(req, resp);
            }

        }

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

    private boolean createPointOfInterest(HttpServletRequest req) throws IOException {
        String stringLat = req.getParameter("lat");
        String stringLng = req.getParameter("lng");
        String name = req.getParameter("name");
        String borough = req.getParameter("borough");
        String stringSideOfStreet = req.getParameter("side_of_street");
        String poiType = req.getParameter("type");
        
        try {
            PointOfInterest point = new PointOfInterest(0, fixFloat(stringLat), fixFloat(stringLng), name, borough, fixInt(stringSideOfStreet), poiType);
            pointOfInterestDAO.create(point);
            System.out.println(point);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean createAirBNB(HttpServletRequest req) throws IOException {
        String stringLat = req.getParameter("lat");
        String stringLng = req.getParameter("lng");
        String name = req.getParameter("name");
        String borough = req.getParameter("borough");
        String neighborhood = req.getParameter("neighborhood");
        String price = req.getParameter("price");
        String reviewsPerMonth = req.getParameter("reviews_per_month");
        String roomType = req.getParameter("room_type");
        String hostID = req.getParameter("host_id");
        String hostname = req.getParameter("host_name");
        String reviews = req.getParameter("reviews");
        try {
            AirBNB airbnb = new AirBNB(0, fixFloat(stringLat), fixFloat(stringLng), borough,
                    neighborhood, fixInt(price), fixFloat(reviewsPerMonth), roomType, fixInt(hostID), hostname, name, fixInt(reviews));
            airBNBDAO.create(airbnb);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean createBusiness(HttpServletRequest req) throws IOException {
        String stringLat = req.getParameter("lat");
        String stringLng = req.getParameter("lng");
        String name = req.getParameter("name");
        String borough = req.getParameter("borough");
        String industry = req.getParameter("industry");
        String addressbuilding = req.getParameter("address_building");
        String addressstreet = req.getParameter("address_street");
        String city = req.getParameter("city");
        String state = req.getParameter("state");
        String stringZipCode = req.getParameter("zip_code");
        String phoneNumber = req.getParameter("phone_number");
        try {
            Business business = new Business(0,
                    fixFloat(stringLat),
                    fixFloat(stringLng),
                    industry,
                    name,
                    addressbuilding,
                    addressstreet,
                    city,
                    state,
                    fixInt(stringZipCode),
                    (long) fixFloat(phoneNumber),
                    borough);
            businessDAO.create(business);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean createCollision(HttpServletRequest req) throws IOException {
        String stringLat = req.getParameter("lat");
        String stringLng = req.getParameter("lng");
        String borough = req.getParameter("borough");
        String peopleInjured = req.getParameter("people_injured");
        String peopleKilled = req.getParameter("people_killed");
        String pedestriansInjured = req.getParameter("pedestrians_injured");
        String pedestriansKilled = req.getParameter("pedestrians_killed");
        String cyclistsInjured = req.getParameter("cyclists_injured");
        String cyclistsKilled = req.getParameter("cyclists_killed");
        String motoristsInjured = req.getParameter("motorists_injured");
        String motoristsKilled = req.getParameter("motorists_killed");
        String stringZipCode = req.getParameter("zip_code");
        String stringDate = req.getParameter("date");

        try {
            Collision collision = new Collision(0,
                    fixFloat(stringLat),
                    fixFloat(stringLng),
                    fixDate(stringDate),
                    borough,
                    fixInt(stringZipCode),
                    fixInt(peopleInjured),
                    fixInt(peopleKilled),
                    fixInt(pedestriansInjured),
                    fixInt(pedestriansKilled),
                    fixInt(cyclistsInjured),
                    fixInt(cyclistsKilled),
                    fixInt(motoristsInjured),
                    fixInt(motoristsKilled)
            );
            collisionDAO.create(collision);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean createEmergencyResponse(HttpServletRequest req) throws IOException {
        String stringLat = req.getParameter("lat");
        String stringLng = req.getParameter("lng");
        String incidentType = req.getParameter("incident_type");
        String borough = req.getParameter("borough");
        String location = req.getParameter("location");
        String createdDate = req.getParameter("created_cate");

        try {
            EmergencyResponse er = new EmergencyResponse(0,
                    fixFloat(stringLat),
                    fixFloat(stringLng),
                    incidentType,
                    location,
                    borough,
                    fixDate(createdDate));
            emergencyResponseDAO.create(er);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean createGarden(HttpServletRequest req) throws IOException {
        String stringLat = req.getParameter("lat");
        String stringLng = req.getParameter("lng");
        String name = req.getParameter("name");
        String borough = req.getParameter("borough");
        String zipCode = req.getParameter("zip_code");
        String address = req.getParameter("address");
        String neighborhoodName = req.getParameter("neighborhood_name");

        try {
            Garden garden = new Garden(0,
                    fixFloat(stringLat),
                    fixFloat(stringLng),
                    name,
                    fixInt(zipCode),
                    address,
                    neighborhoodName,
                    borough);
            gardenDAO.create(garden);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean createGraffiti(HttpServletRequest req) throws IOException {
        String stringLat = req.getParameter("lat");
        String stringLng = req.getParameter("lng");
        String created = req.getParameter("created");
        String borough = req.getParameter("borough");
        String zipCode = req.getParameter("zip_code");
        String address = req.getParameter("address");

        try {
            Graffiti graffiti = new Graffiti(0,
                    fixFloat(stringLat),
                    fixFloat(stringLng),
                    address, borough, fixInt(zipCode), fixDate(created));
            graffitiDAO.create(graffiti);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean createMarket(HttpServletRequest req) throws IOException {
        String stringLat = req.getParameter("lat");
        String stringLng = req.getParameter("lng");
        String accountName = req.getParameter("name");
        String tradeName = req.getParameter("borough");
        String zipCode = req.getParameter("zip_code");
        String address = req.getParameter("address");
        String city = req.getParameter("city");
        String state = req.getParameter("state");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String marketField = req.getParameter("market_field");
        String type = req.getParameter("type");
        String date = req.getParameter("date");

        try {
            Market market = new Market(0, fixFloat(stringLat), fixFloat(stringLng),
                    fixDate(date), accountName, tradeName, address, city, state,
                    fixInt(zipCode), phone, email, marketField, type);
            marketDAO.create(market);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean createPark(HttpServletRequest req) throws IOException {
        String stringLat = req.getParameter("lat");
        String stringLng = req.getParameter("lng");
        String name = req.getParameter("name");
        String landUse = req.getParameter("land_use");

        try {
            Park park = new Park(0, fixFloat(stringLat), fixFloat(stringLng),
                    name, landUse);
            parkDAO.create(park);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

}
