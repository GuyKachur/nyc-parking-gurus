package nyc.dal;

import nyc.model.Destination;
import nyc.model.Market;
import tools.ConnectionManager;

import java.sql.*;

public class MarketDAO extends DestinationDAO {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static MarketDAO instance = null;

    protected MarketDAO() {
        connectionManager = new ConnectionManager();
    }

    public static MarketDAO getInstance() {
        if (instance == null) {
            instance = new MarketDAO();
        }
        return instance;
    }

    public Market create(Market Market) throws SQLException {
        Destination DestMarket = new Destination(Market.getKey(), Market.getLat(), Market.getLng(), Market.getType());
        create(DestMarket);
        Market.setKey(DestMarket.getKey());
        String insertMarket =
                "INSERT INTO market(marketpk, createddate, accountname, tradename, address, city, state, zipcode, phone, email, market, markettype)" +
                        "VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertMarket);
            insertStmt.setFloat(1, Market.getKey());
            insertStmt.setTimestamp(2, new Timestamp(Market.getCreated().getTime()));
            insertStmt.setString(3, Market.getAccountName());
            insertStmt.setString(4, Market.getTradeName());
            insertStmt.setString(5, Market.getAddress());
            insertStmt.setString(6, Market.getCity());
            insertStmt.setString(7, Market.getState());
            insertStmt.setInt(8, Market.getZipCode());
            insertStmt.setString(9, Market.getPhone());
            insertStmt.setString(10, Market.getEmail());
            insertStmt.setString(11, Market.getMarket());
            insertStmt.setString(12, Market.getMarketType());

            insertStmt.executeUpdate();
            return Market;
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
//marketpk, createddate, accountname, tradename, address, city, state, zipcode, phone, email, market, markettype
    public Market getMarketByMarketID(long MarketID) throws SQLException {
        String selectMarket =
                "SELECT MarketPK, AccountName, TradeName, Address, City, State, zipcode, phone, email, market, marketType, Latitude, Longitude " +
                        "FROM market " +
                        "JOIN destination d on market.MarketPK = d.DestinationPK " +
                        "WHERE MarketPK=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectMarket);
            selectStmt.setLong(1, MarketID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                //marketpk, createddate, accountname, tradename, address, city, state, zipcode, phone, email, market, markettype
                long MarketKey = results.getLong("MarketPK");
                float lat = results.getFloat("Latitude");
                float lng = results.getFloat("Longitude");
                Date created = results.getDate("createddate");

                String accountName = results.getString("AccountName");
                String tradeName = results.getString("TradeName");
                String address = results.getString("Address");
                String city = results.getString("City");

                String state = results.getString("State");
                String email = results.getString("email");
                String market = results.getString("market");
                String marketType = results.getString("marketType");


                int zip = results.getInt("zipcode");
                String phone = results.getString("phone");

                Market Market = new Market(MarketKey, lat, lng, created, accountName, tradeName, address, city, state, zip, phone, email, market, marketType);
                return Market;
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

    public Market updateCOL(Market Market, String columnName, String updateValue) throws SQLException {
        String updateMarket = "UPDATE market SET ?=? WHERE MarketPK=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateMarket);
            updateStmt.setString(1, columnName);

            updateStmt.setLong(3, Market.getKey());
            updateStmt.executeUpdate();

            switch (columnName) {
                case "lat":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Market.setLat(Float.parseFloat(updateValue));
                    break;
                case "lng":
                    updateStmt.setFloat(2, Float.parseFloat(updateValue));
                    Market.setLng(Float.parseFloat(updateValue));
                    break;
                case "key":
                    updateStmt.setLong(2, Long.parseLong(updateValue));
                    Market.setKey(Long.parseLong(updateValue));
                    break;
                case "type":
                    updateStmt.setString(2, updateValue);
                    Market.setType(Destination.destinationType.valueOf(updateValue));
                    break;
                    //marketpk, createddate, accountname, tradename, address, city,
                // state, zipcode, phone, email, market, markettype
                case "date":
                    updateStmt.setTimestamp(2, Timestamp.valueOf(updateValue));
                    Market.setCreated(Date.valueOf(updateValue));
                    break;
                case "accountname":
                    updateStmt.setString(2, updateValue);
                    Market.setAccountName(updateValue);
                    break;
                case "tradename":
                    updateStmt.setString(2, updateValue);
                    Market.setTradeName(updateValue);
                    break;
                case "address":
                    updateStmt.setString(2, updateValue);
                    Market.setAddress(updateValue);
                    break;
                case "city":
                    updateStmt.setString(2, updateValue);
                    Market.setCity(updateValue);
                case "state":
                    updateStmt.setString(2, updateValue);
                    Market.setState(updateValue);
                    break;
                case "zipcode":
                    updateStmt.setInt(2, Integer.parseInt(updateValue));
                    Market.setZipCode(Integer.parseInt(updateValue));
                    break;
                case "phone":
                    updateStmt.setString(2, updateValue);
                    Market.setPhone(updateValue);
                    break;
                case "email":
                    updateStmt.setString(2, updateValue);
                    Market.setEmail(updateValue);
                    break;
                case "market":
                    updateStmt.setString(2, updateValue);
                    Market.setMarket(updateValue);
                    break;
                case "markettype":
                    updateStmt.setString(2, updateValue);
                    Market.setMarketType(updateValue);
                    break;
                default:
                    throw new SQLException("COl [" + columnName + "] not found");
            }
            return Market;
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
     * Deletes the Market from the database
     *
     * @param Market
     * @return
     * @throws SQLException
     */
    public Market delete(Market Market) throws SQLException {
        String deleteMarket = "DELETE FROM market WHERE MarketPK=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteMarket);
            deleteStmt.setLong(1, Market.getKey());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the Markets instance.
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
