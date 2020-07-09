package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Review;
import tools.ConnectionManager;

/**
 * Data access object (DAO) class to interact with the underlying review table in your MySQL
 * instance. This is used to store {@link Review} into your MySQL instance and retrieve
 * {@link Review} from MySQL instance.
 */
public class ReviewDao {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static ReviewDao instance = null;

    protected ReviewDao() {
        connectionManager = new ConnectionManager();
    }

    public static ReviewDao getInstance() {
        if (instance == null) {
            instance = new ReviewDao();
        }
        return instance;
    }

    /**
     * creates review
     * @param review
     * @return
     * @throws SQLException
     */
    public Review create(Review review) throws SQLException {
        String insertReview =
                "INSERT INTO review(Username, RestaurantKey, CreatedWhen, WrittenContent, Rating) " +
                        "VALUES(?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertReview,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, review.getUserName());
            insertStmt.setInt(2, review.getRestaurantKey());
            insertStmt.setTimestamp(3, new Timestamp(new Date().getTime()));
            insertStmt.setString(4, review.getWrittenContent());
            insertStmt.setFloat(5, review.getRating());
            insertStmt.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            resultKey = insertStmt.getGeneratedKeys();
            int reviewID = -1;
            if (resultKey.next()) {
                reviewID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            review.setReviewKey(reviewID);
            return review;
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
     * gets review by ID
     * @param reviewID
     * @return
     * @throws SQLException
     */
    public Review getReviewByID(int reviewID) throws SQLException {
        String selectReview =
                "SELECT ReviewKey, UserName, RestaurantKey, CreatedWhen, WrittenContent, Rating " +
                        "FROM review " +
                        "WHERE ReviewKey=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectReview);
            selectStmt.setInt(1, reviewID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int reviewKey = results.getInt("ReviewKey");
                String userName = results.getString("UserName");
                int restaurantKey = results.getInt("RestaurantKey");
                Date created = results.getTimestamp("CreatedWhen");
                String writtenContent = results.getString("WrittenContent");
                float rating = results.getFloat("Rating");
                return new Review(reviewKey, userName, restaurantKey, created, writtenContent, rating);
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
     * gets reviews by username
     * @param userName
     * @return
     * @throws SQLException
     */
    public List<Review> getReviewsByUserName(String userName) throws SQLException {
        List<Review> reviews = new ArrayList<Review>();
        String selectreviews =
                "SELECT ReviewKey, r.UserName, RestaurantKey, CreatedWhen, WrittenContent, Rating " +
                        "FROM review r " +
                        "JOIN user u ON r.UserName = u.UserName " +
                        "WHERE r.UserName=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectreviews);
            selectStmt.setString(1, userName);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int reviewKey = results.getInt("ReviewKey");
                int restaurantKey = results.getInt("RestaurantKey");
                Date created = results.getTimestamp("CreatedWhen");
                String writtenContent = results.getString("WrittenContent");
                float rating = results.getFloat("Rating");
                reviews.add(new Review(reviewKey, userName, restaurantKey, created, writtenContent, rating));
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
        return reviews;
    }

    /**
     * gets reviews by ID
     * @param restaurantID
     * @return
     * @throws SQLException
     */
    public List<Review> getReviewsByRestaurantID(int restaurantID) throws SQLException {
        List<Review> reviews = new ArrayList<Review>();
        String selectreviews =
                "SELECT ReviewKey, UserName, RestaurantKey, CreatedWhen, WrittenContent, Rating " +
                        "FROM review r " +
                        "WHERE RestaurantKey=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectreviews);
            selectStmt.setInt(1, restaurantID);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int reviewKey = results.getInt("ReviewKey");
                String userName = results.getString("UserName");
                int restaurantKey = results.getInt("RestaurantKey");
                Date created = results.getTimestamp("CreatedWhen");
                String writtenContent = results.getString("WrittenContent");
                float rating = results.getFloat("Rating");
                reviews.add(new Review(reviewKey, userName, restaurantKey, created, writtenContent, rating));
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
        return reviews;
    }


    /**
     * Deletes from the database
     * @param review
     * @return
     * @throws SQLException
     */
    public Review delete(Review review) throws SQLException {
        String deleteReview = "DELETE FROM review WHERE ReviewKey=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteReview);
            deleteStmt.setInt(1, review.getReviewKey());
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
