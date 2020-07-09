package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Restaurant;
import model.Restaurant.CuisineType;
import model.FoodCartRestaurant;
import tools.ConnectionManager;
/**
 * Data access object (DAO) class to interact with the underlying foodcartrestaurants table in your MySQL
 * instance. This is used to store {@link FoodCartRestaurant} into your MySQL instance and retrieve
 * {@link FoodCartRestaurant} from MySQL instance.
 */
public class FoodCartRestaurantDao extends RestaurantDao {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static FoodCartRestaurantDao instance = null;

    protected FoodCartRestaurantDao() {
        connectionManager = new ConnectionManager();
    }

    public static FoodCartRestaurantDao getInstance() {
        if (instance == null) {
            instance = new FoodCartRestaurantDao();
        }
        return instance;
    }


    /** Creates the food cart restaurant in the database
     * @param foodCartRestaurant
     * @return
     * @throws SQLException
     */
    public FoodCartRestaurant create(FoodCartRestaurant foodCartRestaurant) throws SQLException {
        //create superclass
        Restaurant foodcart = new Restaurant(0,
                foodCartRestaurant.getName(),
                foodCartRestaurant.getDescription(),
                foodCartRestaurant.getMenu(),
                foodCartRestaurant.getListedHours(),
                foodCartRestaurant.getIsActive(),
                foodCartRestaurant.getStreet1(),
                foodCartRestaurant.getStreet2(),
                foodCartRestaurant.getCity(),
                foodCartRestaurant.getState(),
                foodCartRestaurant.getZip(),
                foodCartRestaurant.getCuisine(),
                foodCartRestaurant.getCompanyKey());
        create(foodcart);
        foodCartRestaurant.setRestaurantKey(foodcart.getRestaurantKey());
        foodCartRestaurant.setFoodCartKey(foodcart.getRestaurantKey());
        String insertRestaurant =
                "INSERT INTO foodcartrestaurant(FoodCartRestaurantKey, IsLicensed) " +
                        "VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertRestaurant);
            insertStmt.setInt(1, foodcart.getRestaurantKey());
            insertStmt.setBoolean(2, foodCartRestaurant.getIsLicensed());
            insertStmt.executeUpdate();

            return foodCartRestaurant;
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
     * Gets the foodcarad restaurant by ID
     * @param foodCartRestaurantID
     * @return
     * @throws SQLException
     */
    public FoodCartRestaurant getFoodCartRestaurantByID(int foodCartRestaurantID) throws SQLException {
        String selectrestaurant =
                "SELECT RestaurantKey, Name, Description, Menu, ListedHours, IsActive, Street1, Street2, City, State, ZipCode, Cuisine, CompanyKey, FoodCartRestaurantKey, IsLicensed " +
                        "FROM foodcartrestaurant " +
                        "JOIN restaurant r on r.RestaurantKey = foodcartrestaurant.FoodCartRestaurantKey " +
                        "WHERE RestaurantKey=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectrestaurant);
            selectStmt.setInt(1, foodCartRestaurantID);
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
                boolean IsLicensed = results.getBoolean("IsLicensed");
                FoodCartRestaurant restaurant = new FoodCartRestaurant(restaurantKey, name, description, menu, listedHours, isActive, street1, street2, city, state, zip, cuisine, companyKey, IsLicensed);
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
     * Gets the food carts based on company name
     * @param companyName
     * @return
     * @throws SQLException
     */
    public List<FoodCartRestaurant> getFoodCartRestaurantByCompanyName(String companyName) throws SQLException {
        List<FoodCartRestaurant> restaurants = new ArrayList<FoodCartRestaurant>();
        String selectrestaurants =
                "SELECT RestaurantKey, r.Name, r.Description, Menu, ListedHours, IsActive, Street1, Street2, City, State, ZipCode, Cuisine, c.CompanyKey, c.Name, IsLicensed " +
                        "FROM foodcartrestaurant " +
                        "JOIN restaurant r ON foodcartrestaurant.FoodCartRestaurantKey = r.RestaurantKey " +
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
                boolean IsLicensed = results.getBoolean("IsLicensed");
                FoodCartRestaurant restaurant = new FoodCartRestaurant(restaurantKey, name, description, menu, listedHours, isActive, street1, street2, city, state, zip, cuisine, companyKey, IsLicensed);
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
     * Deletes associated restaurant
     * @param restaurant
     * @return
     * @throws SQLException
     */
    public FoodCartRestaurant delete(FoodCartRestaurant restaurant) throws SQLException {
        String deleteSitDownRestaurant = "DELETE FROM foodcartrestaurant WHERE FoodCartRestaurantKey=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteSitDownRestaurant);
            deleteStmt.setInt(1, restaurant.getFoodCartKey());
            int affectedRows = deleteStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No records available to delete for deleteFoodCartRestaurant=" + restaurant.getFoodCartKey());
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