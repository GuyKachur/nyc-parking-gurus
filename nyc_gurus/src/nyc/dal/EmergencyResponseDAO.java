package nyc.dal;

import nyc.model.EmergencyResponse;
import nyc.model.Violation;
import tools.ConnectionManager;

import java.sql.*;

public class EmergencyResponseDAO extends ViolationDAO {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static EmergencyResponseDAO instance = null;

    protected EmergencyResponseDAO() {
        connectionManager = new ConnectionManager();
    }

    public static EmergencyResponseDAO getInstance() {
        if (instance == null) {
            instance = new EmergencyResponseDAO();
        }
        return instance;
    }


    public EmergencyResponse create(EmergencyResponse inputVar) throws SQLException {
        Violation ViolColl = new Violation(inputVar.getKey(), inputVar.getLat(), inputVar.getLng(), inputVar.getType());
        create(ViolColl);
        inputVar.setKey(ViolColl.getKey());
        String insertEmergencyResponse =
                "INSERT INTO emergencyresponse(emergencyresponsepk, incidenttype, location, borough, createddate) " +
                        "VALUES(?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertEmergencyResponse);
            insertStmt.setLong(1, inputVar.getKey());
            insertStmt.setString(2, inputVar.getIncidentType());
            insertStmt.setString(3, inputVar.getLocation());
            insertStmt.setString(4, inputVar.getBorough());
            insertStmt.setTimestamp(5, new Timestamp(inputVar.getCreationDate().getTime()));
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

    //emergencyresponsepk, incidenttype, location, borough, createddate
    public EmergencyResponse getEmergencyResponseByID(long EmergencyResponseID) throws SQLException {
        String selectEmergencyResponse =
                "SELECT (EmergencyResponsePK, IncidentType, Location, Borough, CreatedDate, Longitude, Latitude) " +
                        "FROM EmergencyResponse " +
                        "JOIN destination d on emergencyresponse.EmergencyResponsePK = d.DestinationPK " +
                        "WHERE EmergencyResponsePK=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectEmergencyResponse);
            selectStmt.setLong(1, EmergencyResponseID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                long EmergencyResponseKey = results.getLong("EmergencyResponsePK");
                Date date = results.getDate("CreatedDate");
                float lat = results.getFloat("Latitude");
                float lng = results.getFloat("Longitude");
                String borough = results.getString("Borough");
                String incidentType = results.getString("IncidentType");
                String location = results.getString("Location");

                return new EmergencyResponse(EmergencyResponseKey, lat, lng, incidentType, location, borough, date);
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


    public EmergencyResponse updateCOL(EmergencyResponse EmergencyResponse, String columnName, String updateValue) throws SQLException {
        String updateEmergencyResponse = "UPDATE EmergencyResponse SET ?=? WHERE EmergencyResponsePK=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateEmergencyResponse);
            updateStmt.setString(1, columnName);

            updateStmt.setLong(3, EmergencyResponse.getKey());
            updateStmt.executeUpdate();

            switch (columnName) {
                case "lat":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    EmergencyResponse.setLat(Float.parseFloat(updateValue));
                    break;
                case "lng":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    EmergencyResponse.setLng(Float.parseFloat(updateValue));
                    break;
                case "key":
                    updateStmt.setLong(2, Long.parseLong(updateValue));
                    EmergencyResponse.setKey(Long.parseLong(updateValue));
                    break;
                case "type":
                    updateStmt.setString(2, updateValue);
                    EmergencyResponse.setType(Violation.ViolationType.valueOf(updateValue));
                    break;
                case "created":
                    updateStmt.setTimestamp(2, Timestamp.valueOf(updateValue));
                    EmergencyResponse.setCreationDate(Date.valueOf(updateValue));
                    break;
                case "location":
                    updateStmt.setString(2, updateValue);
                    EmergencyResponse.setLocation(updateValue);
                    break;
                case "borough":
                    updateStmt.setString(2, updateValue);
                    EmergencyResponse.setBorough(updateValue);
                    break;
                case "incidentType":
                    updateStmt.setString(2, updateValue);
                    EmergencyResponse.setIncidentType(updateValue);
                    break;
                default:
                    throw new SQLException("COl [" + columnName + "] not found");
            }
            return EmergencyResponse;
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
     * Deletes the EmergencyResponse from the database
     *
     * @param EmergencyResponse
     * @return
     * @throws SQLException
     */
    public EmergencyResponse delete(EmergencyResponse EmergencyResponse) throws SQLException {
        String deleteEmergencyResponse = "DELETE FROM emergencyresponse WHERE EmergencyResponsePK=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteEmergencyResponse);
            deleteStmt.setLong(1, EmergencyResponse.getKey());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the EmergencyResponses instance.
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
