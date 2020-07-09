package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Restaurant;
import model.Restaurant.CuisineType;
import tools.ConnectionManager;

/**
 * Data access object (DAO) class to interact with the underlying restaurant table in your MySQL
 * instance. This is used to store {@link Restaurant} into your MySQL instance and retrieve
 * {@link Restaurant} from MySQL instance.
 */
public class RestaurantDao {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static RestaurantDao instance = null;

    protected RestaurantDao() {
        connectionManager = new ConnectionManager();
    }

    public static RestaurantDao getInstance() {
        if (instance == null) {
            instance = new RestaurantDao();
        }
        return instance;
    }

    /**
     * inserts the restaurant into the database
     * @param restaurant
     * @return
     * @throws SQLException
     */
    public Restaurant create(Restaurant restaurant) throws SQLException {
        String insertRestaurant =
                "INSERT INTO restaurant(Name, Description, Menu, ListedHours, IsActive, Street1, Street2, City, State, ZipCode, Cuisine, CompanyKey) " +
                        "VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertRestaurant,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, restaurant.getName());
            insertStmt.setString(2, restaurant.getDescription());
            insertStmt.setString(3, restaurant.getMenu());
            insertStmt.setString(4, restaurant.getListedHours());
            insertStmt.setBoolean(5, restaurant.getIsActive());
            insertStmt.setString(6, restaurant.getStreet1());
            insertStmt.setString(7, restaurant.getStreet2());
            insertStmt.setString(8, restaurant.getCity());
            insertStmt.setString(9, restaurant.getState());
            insertStmt.setString(10, restaurant.getZip());
            insertStmt.setString(11, restaurant.getCuisine().toString());
            insertStmt.setInt(12, restaurant.getCompanyKey());
            insertStmt.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            resultKey = insertStmt.getGeneratedKeys();
            int restaurantID = -1;
            if (resultKey.next()) {
                restaurantID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            restaurant.setRestaurantKey(restaurantID);
            return restaurant;
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
     * Gets restaurant by ID
     * @param restaurantID
     * @return
     * @throws SQLException
     */
    public Restaurant getRestaurantByID(int restaurantID) throws SQLException {
        String selectrestaurant =
                "SELECT RestaurantKey, Name, Description, Menu, ListedHours, IsActive, Street1, Street2, City, State, ZipCode, Cuisine, CompanyKey " +
                        "FROM restaurant " +
                        "WHERE RestaurantKey=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectrestaurant);
            selectStmt.setInt(1, restaurantID);
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
                Restaurant restaurant = new Restaurant(restaurantKey, name, description, menu, listedHours, isActive, street1, street2, city, state, zip, cuisine, companyKey);
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
     * Gets restaurants by cuisinety[e
     * @param cuisineType
     * @return
     * @throws SQLException
     */
    public List<Restaurant> getRestaurantsByCuisine(CuisineType cuisineType) throws SQLException {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        String selectrestaurants =
                "SELECT RestaurantKey, Name, Description, Menu, ListedHours, IsActive, Street1, Street2, City, State, ZipCode, Cuisine, CompanyKey " +
                        "FROM restaurant " +
                        "WHERE Cuisine=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectrestaurants);
            selectStmt.setString(1, cuisineType.toString());
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
                Restaurant restaurant = new Restaurant(restaurantKey, name, description, menu, listedHours, isActive, street1, street2, city, state, zip, cuisine, companyKey);
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
     * Gets restaurants by company name
     * @param companyName
     * @return
     * @throws SQLException
     */
    public List<Restaurant> getRestaurantsByCompanyName(String companyName) throws SQLException {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        String selectrestaurants =
                "SELECT RestaurantKey, restaurant.Name, restaurant.Description, Menu, ListedHours, IsActive, Street1, Street2, City, State, ZipCode, Cuisine, company.CompanyKey, company.Name " +
                        "FROM restaurant " +
                        "JOIN company ON company.CompanyKey = restaurant.CompanyKey " +
                        "WHERE company.Name=?;";
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
                Restaurant restaurant = new Restaurant(restaurantKey, name, description, menu, listedHours, isActive, street1, street2, city, state, zip, cuisine, companyKey);
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
     * deletes restaurant from database
     * @param restaurant
     * @return
     * @throws SQLException
     */
    public Restaurant delete(Restaurant restaurant) throws SQLException {
        String deleterestaurant = "DELETE FROM restaurant WHERE restaurantKey=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleterestaurant);
            deleteStmt.setLong(1, restaurant.getRestaurantKey());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the restaurants instance.
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