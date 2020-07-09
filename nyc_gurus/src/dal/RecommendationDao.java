package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Recommendation;
import tools.ConnectionManager;

/**
 * Data access object (DAO) class to interact with the underlying recommendation table in your MySQL
 * instance. This is used to store {@link Recommendation} into your MySQL instance and retrieve
 * {@link Recommendation} from MySQL instance.
 */
public class RecommendationDao {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static RecommendationDao instance = null;

    protected RecommendationDao() {
        connectionManager = new ConnectionManager();
    }

    public static RecommendationDao getInstance() {
        if (instance == null) {
            instance = new RecommendationDao();
        }
        return instance;
    }

    /**
     * Creates teh recommendation
     * @param recommendation
     * @return
     * @throws SQLException
     */
    public Recommendation create(Recommendation recommendation) throws SQLException {
        String insertRecommendation =
                "INSERT INTO recommendation(UserKey, RestaurantKey) " +
                        "VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertRecommendation,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, recommendation.getUserKey());
            insertStmt.setInt(2, recommendation.getRestaurantKey());
            insertStmt.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            resultKey = insertStmt.getGeneratedKeys();
            int recommendationID = -1;
            if (resultKey.next()) {
                recommendationID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            recommendation.setRecommendationKey(recommendationID);
            return recommendation;
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
     * Gets recomendation by ID
     * @param recommendationID
     * @return
     * @throws SQLException
     */
    public Recommendation getRecommendationByID(int recommendationID) throws SQLException {
        String selectrecommendation =
                "SELECT RecommendationKey, UserKey, RestaurantKey " +
                        "FROM recommendation " +
                        "WHERE RecommendationKey=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectrecommendation);
            selectStmt.setInt(1, recommendationID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int recommendationKey = results.getInt("RecommendationKey");
                String userName = results.getString("UserKey");
                int restaurantKey = results.getInt("RestaurantKey");
                return new Recommendation(recommendationKey, userName, restaurantKey);
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
     * Gets recommendations based on username
     * @param userName
     * @return
     * @throws SQLException
     */
    public List<Recommendation> getRecommendationsByUserName(String userName) throws SQLException {
        List<Recommendation> recommendations = new ArrayList<Recommendation>();
        String selectrecommendations =
                "SELECT RecommendationKey, UserKey, RestaurantKey " +
                        "FROM recommendation r " +
//			"JOIN user u ON r.UserName = u.UserName" + we dont really need to do this
                        "WHERE r.UserKey=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectrecommendations);
            selectStmt.setString(1, userName);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int recommendationKey = results.getInt("RecommendationKey");
                int restaurantKey = results.getInt("RestaurantKey");
                recommendations.add(new Recommendation(recommendationKey, userName, restaurantKey));
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
        return recommendations;
    }

    /**
     * Gets recommendation of a restaurant
     * @param restaurantID
     * @return
     * @throws SQLException
     */
    public List<Recommendation> getRecommendationsByRestaurantID(int restaurantID) throws SQLException {
        List<Recommendation> recommendations = new ArrayList<Recommendation>();
        String selectrecommendations =
                "SELECT RecommendationKey, UserKey, RestaurantKey " +
                        "FROM recommendation r " +
                        "WHERE RestaurantKey=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectrecommendations);
            selectStmt.setInt(1, restaurantID);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int recommendationKey = results.getInt("RecommendationKey");
                String userName = results.getString("UserKey");
                int restaurantKey = results.getInt("RestaurantKey");
                recommendations.add(new Recommendation(recommendationKey, userName, restaurantKey));
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
        return recommendations;
    }


    /**
     * Deletes the recommendation
     * @param recommendation
     * @return
     * @throws SQLException
     */
    public Recommendation delete(Recommendation recommendation) throws SQLException {
        String deleterecommendation = "DELETE FROM recommendation WHERE RecommendationKey=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleterecommendation);
            deleteStmt.setInt(1, recommendation.getRecommendationKey());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the users instance.
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
