package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;
import tools.ConnectionManager;

/**
 * Data access object (DAO) class to interact with the underlying user table in your MySQL
 * instance. This is used to store {@link User} into your MySQL instance and retrieve
 * {@link User} from MySQL instance.
 */
public class UserDao {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static UserDao instance = null;

    protected UserDao() {
        connectionManager = new ConnectionManager();
    }

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    /**
     * Save the users instance by storing it in your MySQL instance.
     * This runs a INSERT statement.
     */
    public User create(User user) throws SQLException {
        String insertuser =
                "INSERT INTO user(UserName, PasswordHash, FirstName, LastName, Email, PhoneNum) " +
                        "VALUES(?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertuser);
            insertStmt.setString(1, user.getUserName());
            insertStmt.setString(2, user.getPasswordHash());
            insertStmt.setString(3, user.getFirstName());
            insertStmt.setString(4, user.getLastName());
            insertStmt.setString(5, user.getEmail());
            insertStmt.setString(6, user.getPhoneNum());
            insertStmt.executeUpdate();

            return user;
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
     * Gets the user by username from the sql instance
     * @param userName
     * @return
     * @throws SQLException
     */
    public User getUserByUserName(String userName) throws SQLException {
        String selectuser =
                "SELECT UserName, PasswordHash, FirstName, LastName, Email, PhoneNum " +
                        "FROM user " +
                        "WHERE UserName=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectuser);
            selectStmt.setString(1, userName);
            results = selectStmt.executeQuery();
            if (results.next()) {

                String passwordHash = results.getString("PasswordHash");
                String firstName = results.getString("FirstName");
                String lastName = results.getString("LastName");
                String email = results.getString("Email");
                String phoneNum = results.getString("PhoneNum");
                User user = new User(userName, passwordHash, firstName, lastName, email, phoneNum);
                return user;
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
     * Deletes user from sql instance
     * @param user
     * @return nulled out user
     * @throws SQLException
     */
    public User delete(User user) throws SQLException {
        String deleteuser = "DELETE FROM user WHERE UserName=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteuser);
            deleteStmt.setString(1, user.getUserName());
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
