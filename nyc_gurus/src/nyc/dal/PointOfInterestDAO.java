package nyc.dal;

import nyc.model.PointOfInterest;
import nyc.model.Destination;
import tools.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PointOfInterestDAO extends DestinationDAO {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static PointOfInterestDAO instance = null;

    protected PointOfInterestDAO() {
        connectionManager = new ConnectionManager();
    }

    public static PointOfInterestDAO getInstance() {
        if (instance == null) {
            instance = new PointOfInterestDAO();
        }
        return instance;
    }

    public PointOfInterest create(PointOfInterest PointOfInterest) throws SQLException {
        Destination DestPointOfInterest = new Destination(PointOfInterest.getKey(), PointOfInterest.getLat(), PointOfInterest.getLng(), PointOfInterest.getType());
        create(DestPointOfInterest);
        PointOfInterest.setKey(DestPointOfInterest.getKey());
        String insertPointOfInterest =
                "INSERT INTO point_of_interest(point_of_interestpk, side_of_street, domain, borough, poitype, name)" +
                        "VALUES(?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertPointOfInterest);
            insertStmt.setFloat(1, PointOfInterest.getKey());
            insertStmt.setInt(2, PointOfInterest.getSideOfStreet());
            insertStmt.setString(3, PointOfInterest.getDomain());
            insertStmt.setString(4, PointOfInterest.getBorough());
            insertStmt.setString(5, PointOfInterest.getPOIType());
            insertStmt.setString(6, PointOfInterest.getName());

            insertStmt.executeUpdate();
            return PointOfInterest;
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

    public PointOfInterest getPointOfInterestByPointOfInterestID(long PointOfInterestID) throws SQLException {
        String selectPointOfInterest =
                "SELECT point_of_interestpk, side_of_street, domain, borough, poitype, name, Longitude, Latitude " +
                        "FROM point_of_interest " +
                        "JOIN destination d on point_of_interest.Point_of_InterestPK = d.DestinationPK " +
                        "WHERE Point_of_InterestPK=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectPointOfInterest);
            selectStmt.setLong(1, PointOfInterestID);
            results = selectStmt.executeQuery();
            if (results.next()) {

                long PointOfInterestKey = results.getLong("point_of_interestpk");
                float lat = results.getFloat("Latitude");
                float lng = results.getFloat("Longitude");

                String domain = results.getString("domain");
                String name = results.getString("name");
                String poitype = results.getString("poitype");
                String borough = results.getString("borough");

                int sideOfStreet = results.getInt("side_of_street");
                PointOfInterest PointOfInterest = new PointOfInterest(PointOfInterestKey, lat, lng, name, borough, domain, sideOfStreet, poitype);
                return PointOfInterest;
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

    public PointOfInterest updateCOL(PointOfInterest PointOfInterest, String columnName, String updateValue) throws SQLException {
        String updatePointOfInterest = "UPDATE point_of_interest SET ?=? WHERE Point_of_InterestPK=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updatePointOfInterest);
            updateStmt.setString(1, columnName);

            updateStmt.setLong(3, PointOfInterest.getKey());
            updateStmt.executeUpdate();

            switch (columnName) {
                case "lat":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    PointOfInterest.setLat(Float.parseFloat(updateValue));
                    break;
                case "lng":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    PointOfInterest.setLng(Float.parseFloat(updateValue));
                    break;
                case "key":
                    updateStmt.setLong(2, Long.parseLong(updateValue));
                    PointOfInterest.setKey(Long.parseLong(updateValue));
                    break;
                case "type":
                    updateStmt.setString(2, updateValue);
                    PointOfInterest.setType(Destination.destinationType.valueOf(updateValue));
                    break;
                //point_of_interestpk, side_of_street, domain, borough, poitype, name
                case "domain":
                    updateStmt.setString(2, updateValue);
                    PointOfInterest.setDomain(updateValue);
                    break;
                case "poitype":
                    updateStmt.setString(2, updateValue);
                    PointOfInterest.setPOIType(updateValue);
                    break;
                case "name":
                    updateStmt.setString(2, updateValue);
                    PointOfInterest.setName(updateValue);
                    break;
                case "side_of_street":
                    updateStmt.setInt(2, Integer.parseInt(updateValue));
                    PointOfInterest.setSideOfStreet(Integer.parseInt(updateValue));
                    break;
                case "borough":
                    updateStmt.setString(2, updateValue);
                    PointOfInterest.setBorough(updateValue);
                    break;

                default:
                    throw new SQLException("COl [" + columnName + "] not found");
            }
            return PointOfInterest;
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
     * Deletes the PointOfInterest from the database
     *
     * @param PointOfInterest
     * @return
     * @throws SQLException
     */
    public PointOfInterest delete(PointOfInterest PointOfInterest) throws SQLException {
        String deletePointOfInterest = "DELETE FROM point_of_interest WHERE Point_of_InterestPK=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deletePointOfInterest);
            deleteStmt.setLong(1, PointOfInterest.getKey());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the PointOfInterests instance.
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
