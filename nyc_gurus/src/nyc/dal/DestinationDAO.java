package nyc.dal;

import nyc.model.Destination;
import nyc.tools.ConnectionManager;

import java.sql.*;

public class DestinationDAO {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static DestinationDAO instance = null;

    protected DestinationDAO() {
        connectionManager = new ConnectionManager();
    }

    public static DestinationDAO getInstance() {
        if (instance == null) {
            instance = new DestinationDAO();
        }
        return instance;
    }
    public Destination create(Destination Destination) throws SQLException {
        String insertDestination =
                "INSERT INTO Destination(latitude, longitude, destinationtype) " +
                        "VALUES(?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertDestination,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setFloat(1, Destination.getLat());
            insertStmt.setFloat(2, Destination.getLng());
            insertStmt.setString(3, Destination.getType().name());
            insertStmt.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            resultKey = insertStmt.getGeneratedKeys();
            int DestinationID = -1;
            if (resultKey.next()) {
                DestinationID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            Destination.setKey(DestinationID);
            return Destination;
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

    public Destination getDestinationByDestinationID(long DestinationID) throws SQLException {
        String selectDestination =
                "SELECT destinationpk, latitude, longitude, destinationtype " +
                        "FROM Destination " +
                        "WHERE DestinationPK=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectDestination);
            selectStmt.setLong(1, DestinationID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                long DestinationKey = results.getLong("DestinationPK");
                float lat = results.getFloat("latitude");
                float lng = results.getFloat("longitude");
                Destination.destinationType type = Destination.destinationType.valueOf(results.getString("type"));
               Destination Destination = new Destination( DestinationKey, lat, lng, type);
               Destination.setKey(DestinationID);
               return Destination;
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

    public Destination updateCOL(Destination Destination, String columnName, String updateValue) throws SQLException {
        String updateDestination = "UPDATE Destination SET ?=? WHERE DestinationPK=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateDestination);
            updateStmt.setString(1, columnName);

            updateStmt.setLong(3, Destination.getKey());
            updateStmt.executeUpdate();

            switch(columnName) {
                case "lat":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Destination.setLat(Float.parseFloat(updateValue));
                    break;
                case "lng":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Destination.setLng(Float.parseFloat(updateValue));
                    break;
                case "key":
                    updateStmt.setLong(2, Long.parseLong(updateValue));
                    Destination.setKey(Long.parseLong(updateValue));
                    break;
                case "type":
                    updateStmt.setString(2, updateValue);
                    Destination.setType(nyc.model.Destination.destinationType.valueOf(updateValue));
                    break;
                default:
                    throw new SQLException("COl [" + columnName + "] not found");
            }
            return Destination;
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
     * Deletes the Destination from the database
     * @param Destination
     * @return
     * @throws SQLException
     */
    public Destination delete(Destination Destination) throws SQLException {
        String deleteDestination = "DELETE FROM Destination WHERE Destinationpk=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteDestination);
            deleteStmt.setLong(1, Destination.getKey());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the Destinations instance.
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
