package nyc.dal;

import nyc.model.Park;
import nyc.model.Destination;
import tools.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParkDAO extends DestinationDAO {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static ParkDAO instance = null;

    protected ParkDAO() {
        connectionManager = new ConnectionManager();
    }

    public static ParkDAO getInstance() {
        if (instance == null) {
            instance = new ParkDAO();
        }
        return instance;
    }

    public Park create(Park Park) throws SQLException {
        Destination DestPark = new Destination(Park.getKey(), Park.getLat(), Park.getLng(), Park.getType());
        create(DestPark);
        Park.setKey(DestPark.getKey());
        String insertPark =
                "INSERT INTO Park(parkpk, park_name, landuse) " +
                        "VALUES(?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertPark);
            insertStmt.setFloat(1, Park.getKey());
            insertStmt.setString(2, Park.getName());
            insertStmt.setString(3, Park.getLandUse());
            insertStmt.executeUpdate();
            return Park;
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

    public Park getParkByParkID(long ParkID) throws SQLException {
        String selectPark =
                "SELECT  ParkPK, park_name, landuse, Longitude, Latitude " +
                        "FROM park " +
                        "JOIN destination d on park.ParkPK = d.DestinationPK " +
                        "WHERE ParkPK=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectPark);
            selectStmt.setLong(1, ParkID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                long ParkKey = results.getLong("ParkPK");
                float lat = results.getFloat("Latitude");
                float lng = results.getFloat("Longitude");

                String park_name = results.getString("park_name");
                String landuse = results.getString("landuse");
                Park Park = new Park(ParkKey, lat, lng, park_name, landuse);
                return Park;
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

    public Park updateCOL(Park Park, String columnName, String updateValue) throws SQLException {
        String updatePark = "UPDATE park SET ?=? WHERE ParkPK=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updatePark);
            updateStmt.setString(1, columnName);

            updateStmt.setLong(3, Park.getKey());
            updateStmt.executeUpdate();

            switch (columnName) {
                case "lat":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Park.setLat(Float.parseFloat(updateValue));
                    break;
                case "lng":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Park.setLng(Float.parseFloat(updateValue));
                    break;
                case "key":
                    updateStmt.setLong(2, Long.parseLong(updateValue));
                    Park.setKey(Long.parseLong(updateValue));
                    break;
                case "type":
                    updateStmt.setString(2, updateValue);
                    Park.setType(Destination.destinationType.valueOf(updateValue));
                    break;
                case "park_name":
                    updateStmt.setString(2, updateValue);
                    Park.setName(updateValue);
                    break;
                case "landuse":
                    updateStmt.setString(2, updateValue);
                    Park.setLandUse(updateValue);
                    break;
                default:
                    throw new SQLException("COl [" + columnName + "] not found");
            }
            return Park;
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
     * Deletes the Park from the database
     *
     * @param Park
     * @return
     * @throws SQLException
     */
    public Park delete(Park Park) throws SQLException {
        String deletePark = "DELETE FROM park WHERE Parkpk=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deletePark);
            deleteStmt.setLong(1, Park.getKey());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the Parks instance.
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
