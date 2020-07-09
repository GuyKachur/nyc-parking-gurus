package nyc.dal;

import nyc.model.AirBNB;
import nyc.model.Destination;
import tools.ConnectionManager;

import java.sql.*;

public class AirBNBDAO extends DestinationDAO {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static AirBNBDAO instance = null;

    protected AirBNBDAO() {
        connectionManager = new ConnectionManager();
    }

    public static AirBNBDAO getInstance() {
        if (instance == null) {
            instance = new AirBNBDAO();
        }
        return instance;
    }
    public AirBNB create(AirBNB airbnb) throws SQLException {
        Destination DestAirBNB = new Destination(airbnb.getKey(), airbnb.getLat(), airbnb.getLng(), airbnb.getType());
        create(DestAirBNB);
        airbnb.setKey(DestAirBNB.getKey());
        String insertAirbnb =
                "INSERT INTO Airbnb(Airbnbpk, name, host_name, host_id, room_type, borough, neighborhood, reviews_per_month, price, reviews) " +
                        "VALUES(?,?,?,?,?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertAirbnb);
            insertStmt.setLong(1, airbnb.getKey());
            insertStmt.setString(2, airbnb.getName());
            insertStmt.setString(3, airbnb.getHostName());
            insertStmt.setInt(4, airbnb.getHostID());
            insertStmt.setString(5, airbnb.getRoomType());
            insertStmt.setString(6, airbnb.getBorough());
            insertStmt.setString(7, airbnb.getNeighborHood());
            insertStmt.setFloat(8, airbnb.getReviewPerMonth());
            insertStmt.setInt(9, airbnb.getPrice());
            insertStmt.setInt(10, airbnb.getReviews());

            insertStmt.executeUpdate();
            return airbnb;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (insertStmt != null) {
                insertStmt.close();
            }
        }
    }

    public AirBNB getAirbnbByAirbnbID(long AirbnbID) throws SQLException {
        String selectAirbnb =
                "SELECT airbnbpk, Latitude, Longitude, name, host_name, host_id, room_type," +
                        " borough, neighborhood, reviews, reviews_per_month, price," +
                        " destinationpk, latitude, longitude, destinationtype " +
                        "FROM Airbnb " +
                        "JOIN destination d on d.DestinationPK = airbnbpk " +
                        "WHERE airbnbpk=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectAirbnb);
            selectStmt.setLong(1, AirbnbID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                long AirbnbKey = results.getLong("airbnbpk");
                float lat = results.getFloat("latitude");
                float lng = results.getFloat("longitude");
                String name = results.getString("name");
                String hostName = results.getString("host_name");
                int host_id = results.getInt("host_id");
                String room_type = results.getString("room_type");
                String borough = results.getString("borough");
                String neighborHood = results.getString("neighborhood");
                int reviews = results.getInt("reviews");
                int price = results.getInt("price");
                float reviewsPerMonth = results.getFloat("reviews_per_month");
                AirBNB Airbnb = new AirBNB(AirbnbKey, lat, lng, borough, neighborHood, price, reviewsPerMonth, room_type, host_id, hostName, name, reviews);
                return Airbnb;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return null;
    }

    public AirBNB  updateCOL( AirBNB Airbnb, String columnName, String updateValue) throws SQLException {
        String updateAirbnb = "UPDATE Airbnb SET ?=? WHERE Airbnbpk=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateAirbnb);
            updateStmt.setString(1, columnName);
            updateStmt.setLong(3, Airbnb.getKey());
            updateStmt.executeUpdate();

            switch(columnName) {
                case "lat":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Airbnb.setLat(Float.parseFloat(updateValue));
                    break;
                case "lng":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Airbnb.setLng(Float.parseFloat(updateValue));
                    break;
                case "key":
                    updateStmt.setLong(2, Long.parseLong(updateValue));
                    Airbnb.setKey(Long.parseLong(updateValue));
                    break;
                case "borough":
                    updateStmt.setString(2, updateValue);
                    Airbnb.setBorough(updateValue);
                    break;
                case "name":
                    updateStmt.setString(2, updateValue);
                    Airbnb.setName(updateValue);
                    break;
                case "host_name":
                    updateStmt.setString(2, updateValue);
                    Airbnb.setHostName(updateValue);
                    break;
                case "room_type":
                    updateStmt.setString(2, updateValue);
                    Airbnb.setRoomType(updateValue);
                    break;
                case "neighborhood":
                    updateStmt.setString(2, updateValue);
                    Airbnb.setNeighborHood(updateValue);
                    break;
                case "reviews_per_month":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Airbnb.setReviewPerMonth(Float.parseFloat(updateValue));
                    break;
                // host_id,  price, reviews
                case "host_id":
                    updateStmt.setInt(2, Integer.parseInt(updateValue));
                    Airbnb.setHostID(Integer.parseInt(updateValue));
                    break;
                case "reviews":
                    updateStmt.setInt(2, Integer.parseInt(updateValue));
                    Airbnb.setReviews(Integer.parseInt(updateValue));
                    break;
                case "price":
                    updateStmt.setInt(2, Integer.parseInt(updateValue));
                    Airbnb.setPrice(Integer.parseInt(updateValue));
                    break;
                default:
                    throw new SQLException("COl [" + columnName + "] not found");
            }
            return Airbnb;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (updateStmt != null) {
                updateStmt.close();
            }
        }
    }

    /**
     * Deletes the Airbnb from the database
     * @param Airbnb
     * @return
     * @throws SQLException
     */
    public AirBNB delete(AirBNB Airbnb) throws SQLException {
        String deleteAirbnb = "DELETE FROM Airbnb WHERE Airbnbpk=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteAirbnb);
            deleteStmt.setLong(1, Airbnb.getKey());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the Airbnbs instance.
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }



}
