package nyc.dal;

import nyc.model.Destination;
import nyc.model.Garden;
import nyc.tools.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GardenDAO extends DestinationDAO {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static GardenDAO instance = null;

    protected GardenDAO() {
        connectionManager = new ConnectionManager();
    }

    public static GardenDAO getInstance() {
        if (instance == null) {
            instance = new GardenDAO();
        }
        return instance;
    }

    public Garden create(Garden Garden) throws SQLException {
        Destination DestGarden = new Destination(Garden.getKey(), Garden.getLat(), Garden.getLng(), Garden.getType());
        create(DestGarden);
        Garden.setKey(DestGarden.getKey());
        String insertGarden =
                "INSERT INTO garden(gardenpk, name, address, neighborhood_name, zipcode, borough)" +
                        "VALUES(?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertGarden);
            insertStmt.setFloat(1, Garden.getKey());
            insertStmt.setString(2, Garden.getName());
            insertStmt.setString(3, Garden.getAddress());
            insertStmt.setString(4, Garden.getNeighborhood_name());
            insertStmt.setInt(5, Garden.getZipCode());
            insertStmt.setString(6, Garden.getBorough());
            insertStmt.executeUpdate();
            return Garden;
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
//gardenpk, gardentype, name, address, neighborhood_name, zipcode, borough
    public Garden getGardenByGardenID(long GardenID) throws SQLException {
        String selectGarden =
                "SELECT GardenPK, gardenType, name, address, neighborhood_name, zipcode, borough, Latitude, Longitude " +
                        "FROM garden " +
                        "JOIN destination d on garden.GardenPK = d.DestinationPK " +
                        "WHERE GardenPK=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectGarden);
            selectStmt.setLong(1, GardenID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                long GardenKey = results.getLong("GardenPK");
                float lat = results.getFloat("Latitude");
                float lng = results.getFloat("Longitude");

                String gardenType = results.getString("gardenType");
                String neighborhood_name = results.getString("neighborhood_name");
                String address = results.getString("address");
                String name = results.getString("name");
                String city = results.getString("city");
                String state = results.getString("state");
                String borough = results.getString("borough");

                int zip = results.getInt("zipcode");
                Garden Garden = new Garden(GardenKey, lat, lng, name, zip, address, neighborhood_name, borough);
                return Garden;
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

    public Garden updateCOL(Garden Garden, String columnName, String updateValue) throws SQLException {
        String updateGarden = "UPDATE garden SET ?=? WHERE GardenPK=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateGarden);
            updateStmt.setString(1, columnName);

            updateStmt.setLong(3, Garden.getKey());
            updateStmt.executeUpdate();

            switch (columnName) {
                case "lat":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Garden.setLat(Float.parseFloat(updateValue));
                    break;
                case "lng":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Garden.setLng(Float.parseFloat(updateValue));
                    break;
                case "key":
                    updateStmt.setLong(2, Long.parseLong(updateValue));
                    Garden.setKey(Long.parseLong(updateValue));
                    break;
                case "type":
                    updateStmt.setString(2, updateValue);
                    Garden.setType(Destination.destinationType.valueOf(updateValue));
                    break;
                case "name":
                    updateStmt.setString(2, updateValue);
                    Garden.setName(updateValue);
                    break;
                case "address":
                    updateStmt.setString(2, updateValue);
                    Garden.setAddress(updateValue);
                    break;
                case "neighborhood_name":
                    updateStmt.setString(2, updateValue);
                    Garden.setNeighborhood_name(updateValue);
                    break;
                case "zipcode":
                    updateStmt.setInt(2, Integer.parseInt(updateValue));
                    Garden.setZipCode(Integer.parseInt(updateValue));
                    break;
                case "borough":
                    updateStmt.setString(2, updateValue);
                    Garden.setBorough(updateValue);
                    break;

                default:
                    throw new SQLException("COl [" + columnName + "] not found");
            }
            return Garden;
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
     * Deletes the Garden from the database
     *
     * @param Garden
     * @return
     * @throws SQLException
     */
    public Garden delete(Garden Garden) throws SQLException {
        String deleteGarden = "DELETE FROM garden WHERE GardenPK=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteGarden);
            deleteStmt.setLong(1, Garden.getKey());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the Gardens instance.
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
