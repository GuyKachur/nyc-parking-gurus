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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/update")
public class Update extends HttpServlet {

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
                    req.getRequestDispatcher("/AirBNBUpdate.jsp").forward(req, resp);
                    break;
                case "business":
                    req.getRequestDispatcher("/BusinessUpdate.jsp").forward(req, resp);
                    break;
                case "garden":
                    req.getRequestDispatcher("/GardenUpdate.jsp").forward(req, resp);
                    break;
                case "market":
                    req.getRequestDispatcher("/MarketUpdate.jsp").forward(req, resp);
                    break;
                case "park":
                    req.getRequestDispatcher("/ParkUpdate.jsp").forward(req, resp);
                    break;
                case "point_of_interest":
                    req.getRequestDispatcher("/PointOfInterestUpdate.jsp").forward(req, resp);
                    break;
                case "collision":
                    req.getRequestDispatcher("/CollisionUpdate.jsp").forward(req, resp);
                    break;
                case "emergency_response":
                    req.getRequestDispatcher("/EmergencyResponseUpdate.jsp").forward(req, resp);
                    break;
                case "graffiti":
                    req.getRequestDispatcher("/GraffitiUpdate.jsp").forward(req, resp);
                    break;
                default:
                    req.getRequestDispatcher("/404.jsp").forward(req, resp);
            }

        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	System.out.println("Post: " + req.getQueryString());
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String databaseObject = req.getParameter("object");
        if (databaseObject == null || databaseObject.trim().isEmpty()) {
            messages.put("success", "InvalidObject");
        } else {
            switch (databaseObject.toLowerCase()) {
                case "airbnb":
                    if (UpdateAirBNB(req)) {
                        messages.put("success", "Updated AirBNB");
                    }
                    break;
                case "business":
                    if (UpdateBusiness(req)) {
                        messages.put("success", "Updated Business");
                    }
                    break;
//                case "garden":
//                    if (UpdateGarden(req)) {
//                        messages.put("success", "Updated Garden");
//                    }
//                    break;
//                case "market":
//                    if (UpdateMarket(req)) {
//                        messages.put("success", "Updated Market");
//                    }
//                    break;
//                case "park":
//                    if (UpdatePark(req)) {
//                        messages.put("success", "Updated Market");
//                    }
//                    break;
                case "point_of_interest":
                	PointOfInterest updatedPoint = UpdatePointOfInterest(req);
                    if (updatedPoint != null) {
                        messages.put("success", "Updated Point Of Interest -> " + updatedPoint.getName());
                        List<PointOfInterest> points = new ArrayList<>();
                        points.add(updatedPoint);
                        req.setAttribute("points_of_interest", points );
                    }
                    req.getRequestDispatcher("PointOfInterestFind.jsp").forward(req, resp);
                    break;
//                case "collision":
//                    if (UpdateCollision(req)) {
//                        messages.put("success", "Updated Collision");
//                    }
//                    break;
//                case "emergency_response":
//                    if (UpdateEmergencyResponse(req)) {
//                        messages.put("success", "Updated Emergency Response");
//                    }
//                    break;
//                case "graffiti":
//                    if (UpdateGraffiti(req)) {
//                        messages.put("success", "Updated Graffiti");
//                    }
//                    break;
                default:
                    req.getRequestDispatcher("/404.jsp").forward(req, resp);
            }

        }
        //TODO check this? -> should map the to file names?
//        req.getRequestDispatcher("/update?object=" + databaseObject + ".jsp").forward(req, resp);

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

    private PointOfInterest UpdatePointOfInterest(HttpServletRequest req) throws IOException {
    	System.out.println("A: " + req.getQueryString());
        String stringID = req.getParameter("id");
//        String stringLat = req.getParameter("lat");
//        String stringLng = req.getParameter("lng");
        String name = req.getParameter("name");
        String borough = req.getParameter("borough");
        String stringSideOfStreet = req.getParameter("side_of_street");
        String poiType = req.getParameter("type");
        try {
            PointOfInterest point = pointOfInterestDAO.getPointOfInterestByPointOfInterestID(fixLong(stringID));
            if(point != null) {
//            	if(stringLat != null && !stringLat.trim().isEmpty()) {
//                    pointOfInterestDAO.updateCOL(point, "Latitude", stringLat);
//            	}
//            	if(stringLng != null && !stringLng.trim().isEmpty()) {
//                    pointOfInterestDAO.updateCOL(point, "Longitude", stringLng);
//            	}
            	if(poiType != null && !poiType.trim().isEmpty()) {
                    point = pointOfInterestDAO.updateCOL(point, "POIType", poiType);
            	}
            	if(name != null && !name.trim().isEmpty()) {
            		System.out.println("Updating name: ");
            		point =  pointOfInterestDAO.updateCOL(point, "name", name);
            	}
            	if(borough != null && !borough.trim().isEmpty()) {
            		point = pointOfInterestDAO.updateCOL(point, "borough", borough);
            	}
            	if(stringSideOfStreet != null && !stringSideOfStreet.trim().isEmpty()) {
            		point = pointOfInterestDAO.updateCOL(point, "side_of_street", stringSideOfStreet);
            	}
            }
            return point;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    private boolean UpdateAirBNB(HttpServletRequest req) throws IOException {
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
        String id = req.getParameter("id");
        try {
            AirBNB airbnb = airBNBDAO.getAirbnbByAirbnbID(fixLong(id));
            airBNBDAO.updateCOL(airbnb, "lat", stringLat);
            airBNBDAO.updateCOL(airbnb, "lng", stringLng);
            airBNBDAO.updateCOL(airbnb, "name", name);
            airBNBDAO.updateCOL(airbnb, "borough", borough);
            airBNBDAO.updateCOL(airbnb, "neighborhood", neighborhood );
            airBNBDAO.updateCOL(airbnb, "price", price);
            airBNBDAO.updateCOL(airbnb, "reviews_per_month", reviewsPerMonth);
            airBNBDAO.updateCOL(airbnb, "room_type", roomType);
            airBNBDAO.updateCOL(airbnb, "host_id", hostID);
            airBNBDAO.updateCOL(airbnb, "host_name", hostname);
            airBNBDAO.updateCOL(airbnb, "reviews", reviews);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }

    private boolean UpdateBusiness(HttpServletRequest req) throws IOException {
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
        String id = req.getParameter("id");
        try {
            Business business = businessDAO.getBusinessByBusinessID(fixLong(id));
            businessDAO.updateCOL(business, "lat", stringLat);
            businessDAO.updateCOL(business, "lng", stringLng);
            businessDAO.updateCOL(business, "name", name);
            businessDAO.updateCOL(business, "borough", borough);
            businessDAO.updateCOL(business, "industry", industry);
            businessDAO.updateCOL(business, "addressbuilding", addressbuilding);
            businessDAO.updateCOL(business, "addressstname", addressstreet);
            businessDAO.updateCOL(business, "city", city);
            businessDAO.updateCOL(business, "state", state);
            businessDAO.updateCOL(business, "zipcode", stringZipCode);
            businessDAO.updateCOL(business, "phonenumber", phoneNumber);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        return true;
    }
    //TODO the rest of these
//    private boolean UpdateCollision(HttpServletRequest req) throws IOException {
//        String stringLat = req.getParameter("lat");
//        String stringLng = req.getParameter("lng");
//        String borough = req.getParameter("borough");
//        String peopleInjured = req.getParameter("people_injured");
//        String peopleKilled = req.getParameter("people_killed");
//        String pedestriansInjured = req.getParameter("pedestrians_injured");
//        String pedestriansKilled = req.getParameter("pedestrians_killed");
//        String cyclistsInjured = req.getParameter("cyclists_injured");
//        String cyclistsKilled = req.getParameter("cyclists_killed");
//        String motoristsInjured = req.getParameter("motorists_injured");
//        String motoristsKilled = req.getParameter("motorists_killed");
//        String stringZipCode = req.getParameter("zip_code");
//        String stringDate = req.getParameter("date");
//
//        try {
//            Collision collision = new Collision(0,
//                    fixFloat(stringLat),
//                    fixFloat(stringLng),
//                    fixDate(stringDate),
//                    borough,
//                    fixInt(stringZipCode),
//                    fixInt(peopleInjured),
//                    fixInt(peopleKilled),
//                    fixInt(pedestriansInjured),
//                    fixInt(pedestriansKilled),
//                    fixInt(cyclistsInjured),
//                    fixInt(cyclistsKilled),
//                    fixInt(motoristsInjured),
//                    fixInt(motoristsKilled)
//            );
//            collisionDAO.Update(collision);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new IOException(e);
//        }
//        return true;
//    }
//
//    private boolean UpdateEmergencyResponse(HttpServletRequest req) throws IOException {
//        String stringLat = req.getParameter("lat");
//        String stringLng = req.getParameter("lng");
//        String incidentType = req.getParameter("incident_type");
//        String borough = req.getParameter("borough");
//        String location = req.getParameter("location");
//        String UpdatedDate = req.getParameter("Updated_cate");
//
//        try {
//            EmergencyResponse er = new EmergencyResponse(0,
//                    fixFloat(stringLat),
//                    fixFloat(stringLng),
//                    incidentType,
//                    location,
//                    borough,
//                    fixDate(UpdatedDate));
//            emergencyResponseDAO.Update(er);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new IOException(e);
//        }
//        return true;
//    }
//
//    private boolean UpdateGarden(HttpServletRequest req) throws IOException {
//        String stringLat = req.getParameter("lat");
//        String stringLng = req.getParameter("lng");
//        String name = req.getParameter("name");
//        String borough = req.getParameter("borough");
//        String zipCode = req.getParameter("zip_code");
//        String address = req.getParameter("address");
//        String neighborhoodName = req.getParameter("neighborhood_name");
//
//        try {
//            Garden garden = new Garden(0,
//                    fixFloat(stringLat),
//                    fixFloat(stringLng),
//                    name,
//                    fixInt(zipCode),
//                    address,
//                    neighborhoodName,
//                    borough);
//            gardenDAO.Update(garden);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new IOException(e);
//        }
//        return true;
//    }
//
//    private boolean UpdateGraffiti(HttpServletRequest req) throws IOException {
//        String stringLat = req.getParameter("lat");
//        String stringLng = req.getParameter("lng");
//        String Updated = req.getParameter("Updated");
//        String borough = req.getParameter("borough");
//        String zipCode = req.getParameter("zip_code");
//        String address = req.getParameter("address");
//
//        try {
//            Graffiti graffiti = new Graffiti(0,
//                    fixFloat(stringLat),
//                    fixFloat(stringLng),
//                    address, borough, fixInt(zipCode), fixDate(Updated));
//            graffitiDAO.Update(graffiti);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new IOException(e);
//        }
//        return true;
//    }
//
//    private boolean UpdateMarket(HttpServletRequest req) throws IOException {
//        String stringLat = req.getParameter("lat");
//        String stringLng = req.getParameter("lng");
//        String accountName = req.getParameter("name");
//        String tradeName = req.getParameter("borough");
//        String zipCode = req.getParameter("zip_code");
//        String address = req.getParameter("address");
//        String city = req.getParameter("city");
//        String state = req.getParameter("state");
//        String phone = req.getParameter("phone");
//        String email = req.getParameter("email");
//        String marketField = req.getParameter("market_field");
//        String type = req.getParameter("type");
//        String date = req.getParameter("date");
//
//        try {
//            Market market = new Market(0, fixFloat(stringLat), fixFloat(stringLng),
//                    fixDate(date), accountName, tradeName, address, city, state,
//                    fixInt(zipCode), phone, email, marketField, type);
//            marketDAO.Update(market);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new IOException(e);
//        }
//        return true;
//    }
//
//    private boolean UpdatePark(HttpServletRequest req) throws IOException {
//        String stringLat = req.getParameter("lat");
//        String stringLng = req.getParameter("lng");
//        String name = req.getParameter("name");
//        String landUse = req.getParameter("land_use");
//
//        try {
//            Park park = new Park(0, fixFloat(stringLat), fixFloat(stringLng),
//                    name, landUse);
//            parkDAO.Update(park);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new IOException(e);
//        }
//        return true;
//    }

}
