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

import model.CreditCard;
import tools.ConnectionManager;
/**
 * Data access object (DAO) class to interact with the underlying creditcards table in your MySQL
 * instance. This is used to store {@link CreditCard} into your MySQL instance and retrieve
 * {@link CreditCard} from MySQL instance.
 */
public class CreditCardDao {

    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static CreditCardDao instance = null;

    protected CreditCardDao() {
        connectionManager = new ConnectionManager();
    }

    public static CreditCardDao getInstance() {
        if (instance == null) {
            instance = new CreditCardDao();
        }
        return instance;
    }


    /**
     * Inserts the credit card into the database
     * @param creditCard
     * @return
     * @throws SQLException
     */
    public CreditCard create(CreditCard creditCard) throws SQLException {
        String insertcreditCard =
                "INSERT INTO creditcard(NameOnCard, CardNum, CardExpiration, UserName) " +
                        "VALUES(?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertcreditCard,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, creditCard.getNameOnCard());
            insertStmt.setString(2, creditCard.getCardNum());
            insertStmt.setTimestamp(3, new Timestamp(creditCard.getCardExpiration().getTime()));
            insertStmt.setString(4, creditCard.getUserName());
            insertStmt.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            resultKey = insertStmt.getGeneratedKeys();
            int creditCardID = -1;
            if (resultKey.next()) {
                creditCardID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            creditCard.setCreditCardKey(creditCardID);
            return creditCard;
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
     * Gets the creditcard from the database based on the number
     * @param cardNumber
     * @return
     * @throws SQLException
     */
    public CreditCard getCreditCardByCardNumber(long cardNumber) throws SQLException {
        String selectcreditCard =
                "SELECT CreditCardKey, NameOnCard, CardNum, CardExpiration, UserName " +
                        "FROM creditcard " +
                        "WHERE CardNum=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectcreditCard);
            selectStmt.setLong(1, cardNumber);
            results = selectStmt.executeQuery();
            if (results.next()) {
                long creditCardKey = results.getLong("CreditCardKey");
                String nameOnCard = results.getString("NameOnCard");
                String cardNum = results.getString("CardNum");
                Date cardExpiration = new Date(results.getTimestamp("CardExpiration").getTime());
                String userName = results.getString("UserName");
                CreditCard creditCard = new CreditCard(creditCardKey, nameOnCard, cardNum, cardExpiration, userName);
                return creditCard;
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
     * Resturns a list of credit cards associated with the username
     * @param userName
     * @return
     * @throws SQLException
     */
    public List<CreditCard> getCreditCardsByUserName(String userName) throws SQLException {
        List<CreditCard> creditCards = new ArrayList<CreditCard>();
        String selectCreditCards =
                "SELECT CreditCardKey, NameOnCard, CardNum, CardExpiration, UserName " +
                        "FROM creditcard " +
                        "WHERE UserName=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCreditCards);
            selectStmt.setString(1, userName);
            results = selectStmt.executeQuery();
            while (results.next()) {
                long creditCardKey = results.getLong("CreditCardKey");
                String nameOnCard = results.getString("NameOnCard");
                String cardNum = results.getString("CardNum");
                Date cardExpiration = new Date(results.getTimestamp("CardExpiration").getTime());
                CreditCard creditCard = new CreditCard(creditCardKey, nameOnCard, cardNum, cardExpiration, userName);
                creditCards.add(creditCard);
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
        return creditCards;
    }


    /**
     * Updates the expirationdate on the credit card
     * @param creditCard
     * @param newExpiration
     * @return
     * @throws SQLException
     */
    public CreditCard updateExpiration(CreditCard creditCard, Date newExpiration) throws SQLException {
        String updatecreditCard = "UPDATE creditCard SET CardExpiration=? WHERE creditCardKey=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updatecreditCard);
            updateStmt.setTimestamp(1, new Timestamp(newExpiration.getTime()));
            updateStmt.setLong(2, creditCard.getCreditCardKey());
            updateStmt.executeUpdate();

            // Update the creditCard param before returning to the caller.
            creditCard.setCardExpiration(newExpiration);
            return creditCard;
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
     * Deletes the credit card from the database
     * @param creditCard
     * @return
     * @throws SQLException
     */
    public CreditCard delete(CreditCard creditCard) throws SQLException {
        String deletecreditCard = "DELETE FROM creditCard WHERE creditCardKey=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deletecreditCard);
            deleteStmt.setLong(1, creditCard.getCreditCardKey());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the creditCards instance.
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