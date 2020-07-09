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

import model.Reservation;
import tools.ConnectionManager;

/**
 * Data access object (DAO) class to interact with the underlying reservation table in your MySQL
 * instance. This is used to store {@link Reservation} into your MySQL instance and retrieve
 * {@link Reservation} from MySQL instance.
 */
public class ReservationDao {
    protected ConnectionManager connectionManager;

    // Single pattern: instantiation is limited to one object.
    private static ReservationDao instance = null;

    protected ReservationDao() {
        connectionManager = new ConnectionManager();
    }

    public static ReservationDao getInstance() {
        if (instance == null) {
            instance = new ReservationDao();
        }
        return instance;
    }

    /**
     * Create the reservation
     * @param reservation
     * @return
     * @throws SQLException
     */
    public Reservation create(Reservation reservation) throws SQLException {
        String insertReservation =
                "INSERT INTO reservation(UserName, SitDownRestaurantKey, Start, End, PartySize) " +
                        "VALUES(?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertReservation,
                    Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, reservation.getUserName());
            insertStmt.setInt(2, reservation.getSitDownRestaurantKey());
            insertStmt.setTimestamp(3, new Timestamp(reservation.getStart().getTime()));
            insertStmt.setTimestamp(4, new Timestamp(reservation.getEnd().getTime()));
            insertStmt.setInt(5, reservation.getPartySize());
            insertStmt.executeUpdate();

            // Retrieve the auto-generated key and set it, so it can be used by the caller.
            resultKey = insertStmt.getGeneratedKeys();
            int reservationID = -1;
            if (resultKey.next()) {
                reservationID = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            reservation.setReservationKey(reservationID);
            return reservation;
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
     * Gets reservation based on ID
     * @param reservationID
     * @return
     * @throws SQLException
     */
    public Reservation getReservationByID(int reservationID) throws SQLException {
        String selectreservation =
                "SELECT ReservationKey, UserName, SitDownRestaurantKey, Start, End, PartySize " +
                        "FROM reservation " +
                        "WHERE ReservationKey=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectreservation);
            selectStmt.setInt(1, reservationID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int reservationKey = results.getInt("ReservationKey");
                String userName = results.getString("UserName");
                int restaurantKey = results.getInt("SitDownRestaurantKey");
                Date start = results.getTimestamp("Start");
                Date end = results.getTimestamp("End");
                int partysize = results.getInt("PartySize");
                return new Reservation(reservationKey, userName, restaurantKey, start, end, partysize);
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
     * Gets reservations by username
     * @param userName
     * @return
     * @throws SQLException
     */
    public List<Reservation> getReservationsByUserName(String userName) throws SQLException {
        List<Reservation> reservations = new ArrayList<Reservation>();
        String selectreservations =
                "SELECT ReservationKey, UserName, SitDownRestaurantKey, Start, End, PartySize " +
                        "FROM reservation r " +
                        "WHERE r.UserName=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectreservations);
            selectStmt.setString(1, userName);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int reservationKey = results.getInt("ReservationKey");
                int restaurantKey = results.getInt("SitDownRestaurantKey");
                Date start = results.getTimestamp("Start");
                Date end = results.getTimestamp("End");
                int partysize = results.getInt("PartySize");
                reservations.add(new Reservation(reservationKey, userName, restaurantKey, start, end, partysize));
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
        return reservations;
    }

    /**
     * Gets reservations by sitdownID
     * @param restaurantID
     * @return
     * @throws SQLException
     */
    public List<Reservation> getReservationsBySitDownRestaurantID(int restaurantID) throws SQLException {
        List<Reservation> reservations = new ArrayList<Reservation>();
        String selectreservations =
                "SELECT ReservationKey, UserName, SitDownRestaurantKey, Start, End, PartySize " +
                        "FROM reservation r " +
                        "WHERE SitDownRestaurantKey=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectreservations);
            selectStmt.setInt(1, restaurantID);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int reservationKey = results.getInt("ReservationKey");
                int restaurantKey = results.getInt("SitDownRestaurantKey");
                String userName = results.getString("UserName");
                Date start = results.getTimestamp("Start");
                Date end = results.getTimestamp("End");
                int partysize = results.getInt("PartySize");
                reservations.add(new Reservation(reservationKey, userName, restaurantKey, start, end, partysize));
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
        return reservations;
    }


    /**
     * deletes teh reservation
     * @param reservation
     * @return
     * @throws SQLException
     */
    public Reservation delete(Reservation reservation) throws SQLException {
        String deletereservation = "DELETE FROM reservation WHERE ReservationKey=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deletereservation);
            deleteStmt.setInt(1, reservation.getReservationKey());
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
