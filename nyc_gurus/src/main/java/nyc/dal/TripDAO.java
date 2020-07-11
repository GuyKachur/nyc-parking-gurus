package nyc.dal;

import nyc.model.Trip;
import nyc.tools.ConnectionManager;

import java.sql.*;

public class TripDAO {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static TripDAO instance = null;

    protected TripDAO() {
        connectionManager = new ConnectionManager();
    }

    public static TripDAO getInstance() {
        if (instance == null) {
            instance = new TripDAO();
        }
        return instance;
    }
    public Trip create(Trip Trip) throws SQLException {
        String insertTrip =
                "INSERT INTO trip(start_date, end_date, user_username, destination_destinationpk) " +
                        "VALUES(?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertTrip,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setTimestamp(1, new Timestamp(Trip.getStart().getTime()));
            insertStmt.setTimestamp(2, new Timestamp(Trip.getEnd().getTime()));
            insertStmt.setString(3, Trip.getUserID());
            insertStmt.setInt(4, Trip.getDestinationID());
            insertStmt.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            resultKey = insertStmt.getGeneratedKeys();
            int TripID = -1;
            if (resultKey.next()) {
                TripID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            Trip.setKey(TripID);
            return Trip;
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
            if (resultKey != null) {
                resultKey.close();
            }
        }
    }

    /**
     * Gets the Trip from Mysql by Trip Name
     * @param
     * @return
     * @throws SQLException
     */
    public Trip getTripByTripID(long TripID) throws SQLException {
        String selectTrip =
                "SELECT trippk, start_date, end_date, user_username, destination_destinationpk " +
                        "FROM trip " +
                        "WHERE TripPK=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectTrip);
            selectStmt.setLong(1, TripID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int TripKey = results.getInt("trippk");
//                trippk, start_date, end_date, user_username, destination_destinationpk
                Date start = results.getDate("start_date");
                Date end = results.getDate("end_date");
                String user = results.getString("user_username");
                int destID = results.getInt("destination_destinationpk");
               Trip trip = new Trip(start, end, user, destID);
               trip.setKey(TripKey);
               return trip;
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

    /**
     * Updates the companies description
     * @param Trip
     * @param
     * @return
     * @throws SQLException
     */
    public Trip updateCOL(Trip Trip, String columnName, String updateValue) throws SQLException {
        String updateTrip = "UPDATE trip SET ?=? WHERE TripPK=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateTrip);
            updateStmt.setString(1, columnName);
            updateStmt.setLong(3, Trip.getKey());
            updateStmt.executeUpdate();

            switch(columnName) {

                case "key":
                    updateStmt.setLong(2, Long.parseLong(updateValue));
                    Trip.setKey(Integer.parseInt(updateValue));
                    break;
                case "start":
                    updateStmt.setTimestamp(2, Timestamp.valueOf(updateValue));
                    Trip.setStart(Timestamp.valueOf(updateValue));
                    break;
                case "end":
                    updateStmt.setTimestamp(2, Timestamp.valueOf(updateValue));
                    Trip.setEnd(Timestamp.valueOf(updateValue));
                    break;
                case "user":
                    updateStmt.setString(2, updateValue);
                    Trip.setUserID(updateValue);
                    break;
                case "destination":
                    updateStmt.setInt(2, Integer.parseInt(updateValue));
                    Trip.setDestinationID(Integer.parseInt(updateValue));
                    break;
                default:
                    throw new SQLException("COl [" + columnName + "] not found");
            }
            return Trip;
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
     * Deletes the Trip from the database
     * @param Trip
     * @return
     * @throws SQLException
     */
    public Trip delete(Trip Trip) throws SQLException {
        String deleteTrip = "DELETE FROM violation WHERE violationpk=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteTrip);
            deleteStmt.setLong(1, Trip.getKey());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the Trips instance.
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
