package nyc.dal;

import nyc.model.Graffiti;
import nyc.model.Violation;
import nyc.tools.ConnectionManager;

import java.sql.*;

public class GraffitiDAO extends ViolationDAO {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static GraffitiDAO instance = null;

    protected GraffitiDAO() {
        connectionManager = new ConnectionManager();
    }

    public static GraffitiDAO getInstance() {
        if (instance == null) {
            instance = new GraffitiDAO();
        }
        return instance;
    }


    public Graffiti create(Graffiti inputVar) throws SQLException {
        Violation ViolColl = new Violation(inputVar.getKey(), inputVar.getLat(), inputVar.getLng(), inputVar.getType());
        create(ViolColl);
        inputVar.setKey(ViolColl.getKey());
        String insertGraffiti =
                "INSERT INTO graffiti(graffitipk, incidentaddress, borough, createddate, zipcode) " +
                        "VALUES(?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertGraffiti);
            insertStmt.setLong(1, inputVar.getKey());
            insertStmt.setString(2, inputVar.getIncidentAddress());
            insertStmt.setString(3, inputVar.getBorough());
            insertStmt.setTimestamp(4, new Timestamp(inputVar.getCreated().getTime()));
            insertStmt.setInt(5, inputVar.getZipCode());
            insertStmt.executeUpdate();
            return inputVar;
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

    public Graffiti getGraffitiByID(long GraffitiID) throws SQLException {
        String selectGraffiti =
                "SELECT (GraffitiPK, ZipCode, Borough, CreatedDate, IncidentAddress, Latitude, Longitude )" +
                        "FROM graffiti " +
                        "JOIN destination d on graffiti.GraffitiPK = d.DestinationPK " +
                        "WHERE GraffitiPK=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectGraffiti);
            selectStmt.setLong(1, GraffitiID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                long GraffitiKey = results.getLong("GraffitiPK");
                Date date = results.getDate("CreatedDate");
                float lat = results.getFloat("Latitude");
                float lng = results.getFloat("Longitude");

                int zipCode = results.getInt("ZipCode");
                String borough = results.getString("Borough");
                String address = results.getString("IncidentAddress");

                return new Graffiti(GraffitiKey, lat,lng, address, borough, zipCode, date);
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


    public Graffiti updateCOL(Graffiti Graffiti, String columnName, String updateValue) throws SQLException {
        String updateGraffiti = "UPDATE graffiti SET ?=? WHERE GraffitiPK=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateGraffiti);
            updateStmt.setString(1, columnName);

            updateStmt.setLong(3, Graffiti.getKey());
            updateStmt.executeUpdate();

            switch (columnName) {
                case "lat":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Graffiti.setLat(Float.parseFloat(updateValue));
                    break;
                case "lng":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Graffiti.setLng(Float.parseFloat(updateValue));
                    break;
                case "key":
                    updateStmt.setLong(2, Long.parseLong(updateValue));
                    Graffiti.setKey(Long.parseLong(updateValue));
                    break;
                case "type":
                    updateStmt.setString(2, updateValue);
                    Graffiti.setType(Violation.ViolationType.valueOf(updateValue));
                    break;
                case "created":
                    updateStmt.setTimestamp(2, Timestamp.valueOf(updateValue));
                    Graffiti.setCreated(Date.valueOf(updateValue));
                    break;
                case "zipcode":
                    updateStmt.setInt(2, Integer.parseInt(updateValue));
                    Graffiti.setZipCode(Integer.parseInt(updateValue));
                    break;
                case "borough":
                    updateStmt.setString(2, updateValue);
                    Graffiti.setBorough(updateValue);
                    break;
                    //GraffitiPK, ZipCode, Borough, CreatedDate, IncidentAddress, Latitude, Longitude
                case "address":
                    updateStmt.setString(2, updateValue);
                    Graffiti.setIncidentAddress(updateValue);
                    break;
                default:
                    throw new SQLException("COl [" + columnName + "] not found");
            }
            return Graffiti;
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
     * Deletes the Graffiti from the database
     *
     * @param Graffiti
     * @return
     * @throws SQLException
     */
    public Graffiti delete(Graffiti Graffiti) throws SQLException {
        String deleteGraffiti = "DELETE FROM graffiti WHERE Graffitipk=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteGraffiti);
            deleteStmt.setLong(1, Graffiti.getKey());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the Graffiti's instance.
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
