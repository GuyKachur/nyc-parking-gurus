package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Company;
import tools.ConnectionManager;


/**
 * Data access object (DAO) class to interact with the underlying companys table in your MySQL
 * instance. This is used to store {@link Company} into your MySQL instance and retrieve
 * {@link Companyompany} from MySQL instance.
 */
public class CompanyDao {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static CompanyDao instance = null;

    protected CompanyDao() {
        connectionManager = new ConnectionManager();
    }

    public static CompanyDao getInstance() {
        if (instance == null) {
            instance = new CompanyDao();
        }
        return instance;
    }

    /**
     * Save the companys instance by storing it in your MySQL instance.
     * This runs a INSERT statement.
     */
    public Company create(Company company) throws SQLException {
        String insertCompany =
                "INSERT INTO company(Name, Description) " +
                        "VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertCompany,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, company.getName());
            insertStmt.setString(2, company.getDescription());
            insertStmt.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            resultKey = insertStmt.getGeneratedKeys();
            int companyID = -1;
            if (resultKey.next()) {
                companyID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            company.setCompanyKey(companyID);
            return company;
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
     * Gets the company from Mysql by company Name
     * @param companyName
     * @return
     * @throws SQLException
     */
    public Company getCompanyByCompanyName(String companyName) throws SQLException {
        String selectCompany =
                "SELECT CompanyKey, Name, Description " +
                        "FROM company " +
                        "WHERE Name=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCompany);
            selectStmt.setString(1, companyName);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int CompanyKey = results.getInt("CompanyKey");
                String name = results.getString("Name");
                String description = results.getString("Description");
                Company company = new Company(CompanyKey, name, description);
                return company;
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
     * Updates the companies description
     * @param company
     * @param newDescription
     * @return
     * @throws SQLException
     */
    public Company updateAbout(Company company, String newDescription) throws SQLException {
        String updatecompany = "UPDATE company SET Description=? WHERE CompanyKey=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updatecompany);
            updateStmt.setString(1, newDescription);
            updateStmt.setLong(2, company.getCompanyKey());
            updateStmt.executeUpdate();

            // Update the company param before returning to the caller.
            company.setDescription(newDescription);
            return company;
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
     * Deletes the company from the database
     * @param company
     * @return
     * @throws SQLException
     */
    public Company delete(Company company) throws SQLException {
        String deleteCompany = "DELETE FROM company WHERE CompanyKey=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteCompany);
            deleteStmt.setLong(1, company.getCompanyKey());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the companys instance.
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
