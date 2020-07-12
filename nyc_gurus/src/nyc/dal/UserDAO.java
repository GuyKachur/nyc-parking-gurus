package nyc.dal;

import nyc.model.User;
import nyc.tools.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
		String insertUser = "INSERT INTO User(UserName, PasswordHash, FirstName, LastName) VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser);
			insertStmt.setString(1, user.getUserName());
			insertStmt.setString(2, user.getPasswordHash());
			insertStmt.setString(3, user.getFirstName());
			insertStmt.setString(4, user.getLastName());
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
	 * Update the column of a User instance.
	 * This runs a UPDATE statement.
	 */
	public User updateUser(User user, String colName, String newValue) throws SQLException {
		String updateUser = "UPDATE User SET " + colName + "=? WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUser);
			updateStmt.setString(1, newValue);
			updateStmt.setString(2, user.getUserName());
			updateStmt.executeUpdate();

			// Update the User param before returning to the caller.
			switch (colName.toLowerCase()) {
				case "username":
					user.setUserName(newValue);
					break;
				case "passwordhash":
					user.setPasswordHash(newValue);
					break;
				case "firstname":
					user.setFirstName(newValue);
					break;
				case "lastname":
					user.setLastName(newValue);
					break;
				default:
					throw new SQLException("COl [" + colName + "] not found");
					
			}
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
		String deleteUser = "DELETE FROM User WHERE UserName=?;";
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
		String selectUser = "SELECT UserName, PasswordHash, FirstName, LastName FROM User WHERE Username=?;";
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
				String resultFirstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				User user = new User(resultUserName, passwordHash, resultFirstName, lastName);
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
	
	/**
	 * Get the all the Users for a firstname.
	 */
	public List<User> getUsersByFirstName(String firstName) throws SQLException {
		List<User> users = new ArrayList<User>();
		String selectUsers =
			"SELECT UserName,PasswordHash,FirstName,LastName " +
			"FROM User " +
			"WHERE FirstName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUsers);
			selectStmt.setString(1, firstName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String userName = results.getString("UserName");
				String passwordHash = results.getString("PasswordHash");
				String resultFirstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				
				User user = new User(userName, passwordHash, resultFirstName, lastName);
				users.add(user);
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
		return users;
	}
}
