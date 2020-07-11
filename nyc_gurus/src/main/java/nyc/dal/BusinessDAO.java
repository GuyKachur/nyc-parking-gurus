package nyc.dal;

import nyc.model.Business;
import nyc.model.Destination;
import nyc.tools.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusinessDAO extends DestinationDAO {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static BusinessDAO instance = null;

    protected BusinessDAO() {
        connectionManager = new ConnectionManager();
    }

    public static BusinessDAO getInstance() {
        if (instance == null) {
            instance = new BusinessDAO();
        }
        return instance;
    }

    public Business create(Business Business) throws SQLException {
        Destination DestBusiness = new Destination(Business.getKey(), Business.getLat(), Business.getLng(), Business.getType());
        create(DestBusiness);
        Business.setKey(DestBusiness.getKey());
        String insertBusiness =
                "INSERT INTO Business( businesspk, industry, businessname," +
                        " `address building`, `address st name`, city, state," +
                        " zipcode, `phone number`, `address borough`) " +
                        "VALUES(?,?,?,?,?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertBusiness);
            insertStmt.setFloat(1, Business.getKey());
            insertStmt.setString(2, Business.getIndustry());
            insertStmt.setString(3, Business.getName());
            insertStmt.setString(4, Business.getAddressBuilding());
            insertStmt.setString(5, Business.getAddressStreet());
            insertStmt.setString(6, Business.getCity());
            insertStmt.setString(7, Business.getState());
            insertStmt.setInt(8, Business.getZipCode());
            insertStmt.setLong(9, Business.getPhoneNumber());
            insertStmt.setString(10, Business.getBorough());
            insertStmt.executeUpdate();
            return Business;
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

    public Business getBusinessByBusinessID(long BusinessID) throws SQLException {
        String selectBusiness =
                "SELECT businesspk, industry, businessname," +
                        " `address building`, `address st name`, city, state," +
                        " zipcode, `phone number`, `address borough`, Longitude, Latitude " +
                        "FROM Business " +
                        "JOIN destination d on business.BusinessPK = d.DestinationPK " +
                        "WHERE BusinessPK=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectBusiness);
            selectStmt.setLong(1, BusinessID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                long BusinessKey = results.getLong("BusinessPK");
                float lat = results.getFloat("latitude");
                float lng = results.getFloat("longitude");

                String industry = results.getString("industry");
                String businessname = results.getString("businessname");
                String addressbuilding = results.getString("address building");
                String strName = results.getString("address st name");
                String city = results.getString("city");
                String state = results.getString("state");
                String borough = results.getString("address borough");

                int zip = results.getInt("zipcode");
                long phone = results.getLong("phone number");
                Business Business = new Business(BusinessKey, lat, lng, industry, businessname, addressbuilding, strName, city, state, zip, phone, borough);
                return Business;
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

    public Business updateCOL(Business Business, String columnName, String updateValue) throws SQLException {
        String updateBusiness = "UPDATE Business SET ?=? WHERE BusinessPK=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateBusiness);
            updateStmt.setString(1, columnName);

            updateStmt.setLong(3, Business.getKey());
            updateStmt.executeUpdate();

            switch (columnName) {
                case "lat":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Business.setLat(Float.parseFloat(updateValue));
                    break;
                case "lng":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Business.setLng(Float.parseFloat(updateValue));
                    break;
                case "key":
                    updateStmt.setLong(2, Long.parseLong(updateValue));
                    Business.setKey(Long.parseLong(updateValue));
                    break;
                case "type":
                    updateStmt.setString(2, updateValue);
                    Business.setType(Destination.destinationType.valueOf(updateValue));
                    break;
                case "industry":
                    updateStmt.setString(2, updateValue);
                    Business.setIndustry(updateValue);
                    break;
                case "businessname":
                    updateStmt.setString(2, updateValue);
                    Business.setName(updateValue);
                    break;
                case "address building":
                    updateStmt.setString(2, updateValue);
                    Business.setAddressBuilding(updateValue);
                    break;
                case "address st name":
                    updateStmt.setString(2, updateValue);
                    Business.setAddressStreet(updateValue);
                    break;
                case "city":
                    updateStmt.setString(2, updateValue);
                    Business.setCity(updateValue);
                    break;
                case "state":
                    updateStmt.setString(2, updateValue);
                    Business.setState(updateValue);
                    break;
                case "zipcode":
                    updateStmt.setInt(2, Integer.parseInt(updateValue));
                    Business.setZipCode(Integer.parseInt(updateValue));
                    break;
                case "phone number":
                    updateStmt.setLong(2, Long.parseLong(updateValue));
                    Business.setPhoneNumber(Long.parseLong(updateValue));
                    break;
                case "borough":
                    updateStmt.setString(2, updateValue);
                    Business.setBorough(updateValue);
                    break;

                default:
                    throw new SQLException("COl [" + columnName + "] not found");
            }
            return Business;
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
     * Deletes the Business from the database
     *
     * @param Business
     * @return
     * @throws SQLException
     */
    public Business delete(Business Business) throws SQLException {
        String deleteBusiness = "DELETE FROM Business WHERE Businesspk=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteBusiness);
            deleteStmt.setLong(1, Business.getKey());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the Businesss instance.
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
