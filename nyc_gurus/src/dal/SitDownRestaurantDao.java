package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Restaurant;
import model.Restaurant.CuisineType;
import model.SitDownRestaurant;
import tools.ConnectionManager;

/**
 * Data access object (DAO) class to interact with the underlying sitdownrestaurants table in your MySQL
 * instance. This is used to store {@link SitDownRestaurant} into your MySQL instance and retrieve
 * {@link SitDownRestaurant} from MySQL instance.
 */
public class SitDownRestaurantDao extends RestaurantDao {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static SitDownRestaurantDao instance = null;

    protected SitDownRestaurantDao() {
        connectionManager = new ConnectionManager();
    }

    public static SitDownRestaurantDao getInstance() {
        if (instance == null) {
            instance = new SitDownRestaurantDao();
        }
        return instance;
    }

    /**
     * Creates SitDownRestaurant
     * @param sitDownRestaurant
     * @return
     * @throws SQLException
     */
    public SitDownRestaurant create(SitDownRestaurant sitDownRestaurant) throws SQLException {
        //create superclass
        //maybe assign restaurant and get key that way?
        Restaurant rest = new Restaurant(0, sitDownRestaurant.getName(), sitDownRestaurant.getDescription(),
                sitDownRestaurant.getMenu(),
                sitDownRestaurant.getListedHours(),
                sitDownRestaurant.getIsActive(),
                sitDownRestaurant.getStreet1(),
                sitDownRestaurant.getStreet2(),
                sitDownRestaurant.getCity(),
                sitDownRestaurant.getState(),
                sitDownRestaurant.getZip(),
                sitDownRestaurant.getCuisine(),
                sitDownRestaurant.getCompanyKey());
        create(rest);
        sitDownRestaurant.setRestaurantKey(rest.getRestaurantKey());
        sitDownRestaurant.setSitDownRestaurantKey(rest.getRestaurantKey());
        String insertRestaurant =
                "INSERT INTO sitdownrestaurant(SitDownRestaurantKey, Capacity) " +
                        "VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertRestaurant);
            insertStmt.setInt(1, rest.getRestaurantKey());
            insertStmt.setInt(2, sitDownRestaurant.getCapacity());
            insertStmt.executeUpdate();
            return sitDownRestaurant;
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
     * @param sitDownRestaurantID
     * @return
     * @throws SQLException
     */
    public SitDownRestaurant getSitDownRestaurantByID(int sitDownRestaurantID) throws SQLException {
        String selectrestaurant =
                "SELECT RestaurantKey, Name, Description, Menu, ListedHours, IsActive, Street1, Street2, City, State, ZipCode, Cuisine, CompanyKey, SitDownRestaurantKey, Capacity " +
                        "FROM sitdownrestaurant " +
                        "JOIN restaurant r on r.RestaurantKey = sitdownrestaurant.SitDownRestaurantKey " +
                        "WHERE RestaurantKey=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectrestaurant);
            selectStmt.setInt(1, sitDownRestaurantID);
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
                int capacity = results.getInt("Capacity");
                SitDownRestaurant restaurant = new SitDownRestaurant(restaurantKey, name, description, menu, listedHours, isActive, street1, street2, city, state, zip, cuisine, companyKey, capacity);
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
     * Gets sit down restaurants by companyName
     * @param companyName
     * @return
     * @throws SQLException
     */
    public List<SitDownRestaurant> getSitDownRestaurantByCompanyName(String companyName) throws SQLException {
        List<SitDownRestaurant> restaurants = new ArrayList<SitDownRestaurant>();
        String selectrestaurants =
                "SELECT RestaurantKey, r.Name, r.Description, Menu, ListedHours, IsActive, Street1, Street2, City, State, ZipCode, Capacity, Cuisine, c.CompanyKey, c.Name " +
                        "FROM sitdownrestaurant " +
                        "JOIN restaurant r ON sitdownrestaurant.SitDownRestaurantKey = r.RestaurantKey " +
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
                int capacity = results.getInt("Capacity");
                SitDownRestaurant restaurant = new SitDownRestaurant(restaurantKey, name, description, menu, listedHours, isActive, street1, street2, city, state, zip, cuisine, companyKey, capacity);
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
     * deletes restaurant
     * @param restaurant
     * @return
     * @throws SQLException
     */
    public SitDownRestaurant delete(SitDownRestaurant restaurant) throws SQLException {
        String deleteSitDownRestaurant = "DELETE FROM sitdownrestaurant WHERE SitDownRestaurantKey=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteSitDownRestaurant);
            deleteStmt.setInt(1, restaurant.getRestaurantKey());
            int affectedRows = deleteStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No records available to delete for deleteSitDownRestaurant=" + restaurant.getRestaurantKey());
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