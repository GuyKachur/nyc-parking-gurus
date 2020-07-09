package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Restaurant;
import model.Restaurant.CuisineType;
import model.TakeOutRestaurant;
import tools.ConnectionManager;

/**
 * Data access object (DAO) class to interact with the underlying takeoutrestaurants table in your MySQL
 * instance. This is used to store {@link TakeOutRestaurant} into your MySQL instance and retrieve
 * {@link TakeOutRestaurant} from MySQL instance.
 */
public class TakeOutRestaurantDao extends RestaurantDao {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static TakeOutRestaurantDao instance = null;

    protected TakeOutRestaurantDao() {
        connectionManager = new ConnectionManager();
    }

    public static TakeOutRestaurantDao getInstance() {
        if (instance == null) {
            instance = new TakeOutRestaurantDao();
        }
        return instance;
    }


    /**
     * Creates new take out restaurant
     * @param takeOutRestaurant
     * @return
     * @throws SQLException
     */
    public TakeOutRestaurant create(TakeOutRestaurant takeOutRestaurant) throws SQLException {
        Restaurant rest = new Restaurant(0,
                takeOutRestaurant.getName(),
                takeOutRestaurant.getDescription(),
                takeOutRestaurant.getMenu(),
                takeOutRestaurant.getListedHours(),
                takeOutRestaurant.getIsActive(),
                takeOutRestaurant.getStreet1(),
                takeOutRestaurant.getStreet2(),
                takeOutRestaurant.getCity(),
                takeOutRestaurant.getState(),
                takeOutRestaurant.getZip(),
                takeOutRestaurant.getCuisine(),
                takeOutRestaurant.getCompanyKey());
        create(rest);
        takeOutRestaurant.setRestaurantKey(rest.getRestaurantKey());
        takeOutRestaurant.setTakeOutRestaurantKey(rest.getRestaurantKey());
        String insertRestaurant =
                "INSERT INTO takeoutrestaurant(TakeOutRestaurantKey, MaxWaitMinutes) " +
                        "VALUES(?, ?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertRestaurant);
            insertStmt.setInt(1, rest.getRestaurantKey());
            insertStmt.setInt(2, takeOutRestaurant.getMaxWaitMinutes());
            insertStmt.executeUpdate();
            return takeOutRestaurant;
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
     * Gets take out restaurant by ID
     * @param takeOutRestaurantID
     * @return
     * @throws SQLException
     */
    public TakeOutRestaurant getTakeOutRestaurantByID(int takeOutRestaurantID) throws SQLException {
        String selectrestaurant =
                "SELECT RestaurantKey, Name, Description, Menu, ListedHours, IsActive, Street1, Street2, City, State, ZipCode, Cuisine, CompanyKey, TakeOutRestaurantKey, MaxWaitMinutes " +
                        "FROM takeoutrestaurant " +
                        "JOIN restaurant r on r.RestaurantKey = takeoutrestaurant.TakeOutRestaurantKey " +
                        "WHERE RestaurantKey=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectrestaurant);
            selectStmt.setInt(1, takeOutRestaurantID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int restaurantKey = results.getInt("RestaurantKey");
                String name = results.getString("Name");
                String description = results.getString("Description");
                String menu = results.getString("Menu");
                String listedHours = results.getString("ListedHours");
                boolean isActive = results.getBoolean("IsActive");
                String street1 = results.getString("Street1");
                String street2 = results.getString("Street2");
                String city = results.getString("City");
                String state = results.getString("State");
                String zip = results.getString("ZipCode");
                CuisineType cuisine = CuisineType.valueOf(results.getString("Cuisine"));
                int companyKey = results.getInt("CompanyKey");
                int waitTime = results.getInt("MaxWaitMinutes");
                TakeOutRestaurant restaurant = new TakeOutRestaurant(restaurantKey, name, description, menu, listedHours, isActive, street1, street2, city, state, zip, cuisine, companyKey, waitTime);
                return restaurant;
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
     * Get take out restaurants by company name
     * @param companyName
     * @return
     * @throws SQLException
     */
    public List<TakeOutRestaurant> getTakeOutRestaurantByCompanyName(String companyName) throws SQLException {
        List<TakeOutRestaurant> restaurants = new ArrayList<TakeOutRestaurant>();
        String selectrestaurants =
                "SELECT RestaurantKey, r.Name, r.Description, Menu, ListedHours, IsActive, Street1, Street2, City, State, ZipCode, Cuisine, c.CompanyKey, c.Name, MaxWaitMinutes " +
                        "FROM takeoutrestaurant " +
                        "JOIN restaurant r ON takeoutrestaurant.TakeOutRestaurantKey = r.RestaurantKey " +
                        "JOIN company c ON c.CompanyKey = r.CompanyKey " +
                        "WHERE c.Name=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectrestaurants);
            selectStmt.setString(1, companyName);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int restaurantKey = results.getInt("RestaurantKey");
                String name = results.getString("Name");
                String description = results.getString("Description");
                String menu = results.getString("Menu");
                String listedHours = results.getString("ListedHours");
                boolean isActive = results.getBoolean("IsActive");
                String street1 = results.getString("Street1");
                String street2 = results.getString("Street2");
                String city = results.getString("City");
                String state = results.getString("State");
                String zip = results.getString("ZipCode");
                CuisineType cuisine = CuisineType.valueOf(results.getString("Cuisine"));
                int companyKey = results.getInt("CompanyKey");
                int waitTime = results.getInt("MaxWaitMinutes");
                TakeOutRestaurant restaurant = new TakeOutRestaurant(restaurantKey, name, description, menu, listedHours, isActive, street1, street2, city, state, zip, cuisine, companyKey, waitTime);
                restaurants.add(restaurant);
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
        return restaurants;
    }


    /**
     * Deletes restaurant
     * @param restaurant
     * @return
     * @throws SQLException
     */
    public TakeOutRestaurant delete(TakeOutRestaurant restaurant) throws SQLException {
        String deleteTakeOutRestaurant = "DELETE FROM takeoutrestaurant WHERE TakeOutRestaurantKey=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteTakeOutRestaurant);
            deleteStmt.setInt(1, restaurant.getTakeOutRestaurantKey());
            int affectedRows = deleteStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No records available to delete for deleteTakeOutRestaurant=" + restaurant.getTakeOutRestaurantKey());
            }
            super.delete(restaurant);

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