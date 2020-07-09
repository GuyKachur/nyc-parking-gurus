package nyc.dal;

import nyc.model.*;
import nyc.tools.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static UserDAO instance = null;
	protected UserDAO() {
		connectionManager = new ConnectionManager();
	}
	public static UserDAO getInstance() {
		if(instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}

	/**
	 * Save the User instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 */
	public User create(User user) throws SQLException {
		String insertUser = "INSERT INTO User(Username, passwordhash) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, user.getUserName());
			insertStmt.setString(2, user.getPasswordHash());
			insertStmt.executeUpdate();

			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		}
	}
	
	/**
	 * Update the password hash of the User instance.
	 * This runs a UPDATE statement.
	 */
	public User updateAbout(User user, String newPasswordHash) throws SQLException {
		String updateUser = "UPDATE User SET passwordhash=? WHERE Username=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUser);
			updateStmt.setString(1, newPasswordHash);
			updateStmt.setString(2, user.getUserName());
			updateStmt.executeUpdate();

			// Update the User param before returning to the caller.
			user.setPasswordHash(newPasswordHash);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}

	/**
	 * Delete the User instance.
	 * This runs a DELETE statement.
	 */
	public User delete(User user) throws SQLException {
		String deleteUser = "DELETE FROM User WHERE Username=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setString(1, user.getUserName());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Persons instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}

	/**
	 * Get the User record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Company instance.
	 */
	public User getUserByUserName(String userName) throws SQLException {
		String selectUser = "SELECT Username,passwordhash FROM User WHERE Username=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();

			if(results.next()) {
				String resultUserName = results.getString("Username");
				String passwordHash = results.getString("passwordhash");
				User user = new User(resultUserName, passwordHash);
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
}
