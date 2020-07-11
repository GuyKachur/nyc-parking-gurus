package nyc.dal;

import nyc.model.Violation;
import nyc.tools.ConnectionManager;

import java.sql.*;

public class ViolationDAO {
    // Single pattern: instantiation is limited to one object.
    private static ViolationDAO instance = null;
    protected ConnectionManager connectionManager;

    protected ViolationDAO() {
        connectionManager = new ConnectionManager();
    }

    public static ViolationDAO getInstance() {
        if (instance == null) {
            instance = new ViolationDAO();
        }
        return instance;
    }

    public Violation create(Violation Violation) throws SQLException {
        String insertViolation =
                "INSERT INTO violation(latitude, longitude, violationtype) " +
                        "VALUES(?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertViolation,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setFloat(1, Violation.getLat());
            insertStmt.setFloat(2, Violation.getLng());
            insertStmt.setString(3, Violation.getType().name());
            insertStmt.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            resultKey = insertStmt.getGeneratedKeys();
            int ViolationID = -1;
            if (resultKey.next()) {
                ViolationID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            Violation.setKey(ViolationID);
            return Violation;
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
     * Gets the Violation from Mysql by Violation Name
     *
     * @param
     * @return
     * @throws SQLException
     */
    public Violation getViolationByViolationID(long ViolationID) throws SQLException {
        String selectViolation =
                "SELECT ViolationPK, Latitude, Longitude, violationType " +
                        "FROM violation " +
                        "WHERE ViolationPK=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectViolation);
            selectStmt.setLong(1, ViolationID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                long ViolationKey = results.getLong("violationpk");
                float lat = results.getFloat("latitude");
                float lng = results.getFloat("longitude");
                Violation.ViolationType type = Violation.ViolationType.valueOf(results.getString("type"));
                Violation violation = new Violation(ViolationKey, lat, lng, type);
                return violation;
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
     *
     * @param Violation
     * @param
     * @return
     * @throws SQLException
     */
    public Violation updateCOL(Violation Violation, String columnName, String updateValue) throws SQLException {
        String updateViolation = "UPDATE violation SET ?=? WHERE violationpk=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateViolation);
            updateStmt.setString(1, columnName);
            updateStmt.setLong(3, Violation.getKey());
            updateStmt.executeUpdate();

            switch (columnName) {
                case "lat":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Violation.setLat(Float.parseFloat(updateValue));
                    break;
                case "lng":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Violation.setLng(Float.parseFloat(updateValue));
                    break;
                case "key":
                    updateStmt.setLong(2, Long.parseLong(updateValue));
                    Violation.setKey(Long.parseLong(updateValue));
                    break;
                case "type":
                    updateStmt.setString(2, updateValue);
                    Violation.setType(nyc.model.Violation.ViolationType.valueOf(updateValue));
                    break;
                default:
                    throw new SQLException("COl [" + columnName + "] not found");
            }
            return Violation;
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
     * Deletes the Violation from the database
     *
     * @param Violation
     * @return
     * @throws SQLException
     */
    public Violation delete(Violation Violation) throws SQLException {
        String deleteViolation = "DELETE FROM violation WHERE violationpk=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteViolation);
            deleteStmt.setLong(1, Violation.getKey());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the Violations instance.
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
