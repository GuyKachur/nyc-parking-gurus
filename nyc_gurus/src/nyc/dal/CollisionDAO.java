package nyc.dal;

import nyc.model.Collision;
import nyc.model.User;
import nyc.model.Violation;
import nyc.tools.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollisionDAO extends ViolationDAO {
    // Single pattern: instantiation is limited to one object.
    private static CollisionDAO instance = null;
    protected ConnectionManager connectionManager;

    protected CollisionDAO() {
        connectionManager = new ConnectionManager();
    }

    public static CollisionDAO getInstance() {
        if (instance == null) {
            instance = new CollisionDAO();
        }
        return instance;
    }


    public Collision create(Collision inputVar) throws SQLException {
        Violation ViolColl = new Violation(inputVar.getKey(), inputVar.getLat(), inputVar.getLng(), inputVar.getType());
        create(ViolColl);
        inputVar.setKey(ViolColl.getKey());
        String insertCollision =
                "INSERT INTO collision(collisionpk, date, borough, zipcode, `persons injured`, `persons killed`, `pedestrians injured`, `pedestrians killed`, `cyclists injured`, `cyclists killed`, `motorists injured`, `motorists killed`) " +
                        "VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertCollision);
            insertStmt.setLong(1, inputVar.getKey());
            insertStmt.setTimestamp(2, new Timestamp(inputVar.getDate().getTime()));
            insertStmt.setString(3, inputVar.getBorough());
            insertStmt.setInt(4, inputVar.getZipCode());
            insertStmt.setInt(5, inputVar.getPeopleInjured());
            insertStmt.setInt(6, inputVar.getPeopleKilled());
            insertStmt.setInt(7, inputVar.getPedestriansInjured());
            insertStmt.setInt(8, inputVar.getPedestriansKilled());
            insertStmt.setInt(9, inputVar.getCyclistsInjured());
            insertStmt.setInt(10, inputVar.getCyclistsKilled());
            insertStmt.setInt(11, inputVar.getMotoristsInjured());
            insertStmt.setInt(12, inputVar.getMotoristsKilled());
            insertStmt.executeUpdate();
            return inputVar;
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
        }
    }

    public Collision getCollisionByID(long CollisionID) throws SQLException {
        String selectCollision =
                "SELECT CollisionPK, Date, Borough, ZipCode, `PERSONS INJURED`, `PERSONS KILLED`," +
                        " `PEDESTRIANS INJURED`, `PEDESTRIANS KILLED`, `CYCLISTS INJURED`," +
                        " `CYCLISTS KILLED`, `MOTORISTS INJURED`, `MOTORISTS KILLED`, Latitude, Longitude " +
                        "FROM Collision " +
                        "JOIN destination d on collision.CollisionPK = d.DestinationPK " +
                        "WHERE CollisionPK=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectCollision);
            selectStmt.setLong(1, CollisionID);
            results = selectStmt.executeQuery();
            if (results.next()) {
                long CollisionKey = results.getLong("CollisionPK");
                Date date = results.getDate("Date");
                float lat = results.getFloat("latitude");
                float lng = results.getFloat("longitude");

                int zipCode = results.getInt("ZipCode");
                String borough = results.getString("Borough");

                int persons_injured = results.getInt("PERSONS INJURED");
                int persons_killed = results.getInt("PERSONS KILLED");
                int pedestrians_injured = results.getInt("PEDESTRIANS INJURED");
                int pedestrians_killed = results.getInt("PEDESTRIANS KILLED");
                int cyclists_injured = results.getInt("CYCLISTS INJURED");
                int cyclists_killed = results.getInt("CYCLISTS KILLED");
                int motorists_injured = results.getInt("MOTORISTS INJURED");
                int motorists_killed = results.getInt("MOTORISTS KILLED");

                return new Collision(CollisionKey, lat, lng, date, borough, zipCode, persons_injured, persons_killed, pedestrians_injured, pedestrians_killed, cyclists_injured, cyclists_killed, motorists_injured, motorists_killed);
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

    public Collision updateCollision(Collision Collision, String columnName, String updateValue) throws SQLException {
    	String columnNameEnclosed = "`" + columnName + "`";
        String updateCollision = "UPDATE Collision SET " + columnNameEnclosed + "=? WHERE CollisionPK=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateCollision);
            updateStmt.setLong(2, Collision.getKey());

            switch (columnName.toLowerCase()) {
                case "latitude":
                    updateStmt.setFloat(1, Float.parseFloat(updateValue));
                    Collision.setLat(Float.parseFloat(updateValue));
                    break;
                case "longitude":
                    updateStmt.setFloat(1, Float.parseFloat(updateValue));
                    Collision.setLng(Float.parseFloat(updateValue));
                    break;
                case "date":
                    updateStmt.setTimestamp(1, Timestamp.valueOf(updateValue));
                    Collision.setDate(Date.valueOf(updateValue));
                    break;
                case "zipcode":
                    updateStmt.setInt(1, Integer.parseInt(updateValue));
                    Collision.setZipCode(Integer.parseInt(updateValue));
                    break;
                case "borough":
                    updateStmt.setString(1, updateValue);
                    Collision.setBorough(updateValue);
                    break;
                case "persons injured":
                    updateStmt.setInt(1, Integer.parseInt(updateValue));
                    Collision.setPeopleInjured(Integer.parseInt(updateValue));
                    break;
                case "persons killed":
                    updateStmt.setInt(1, Integer.parseInt(updateValue));
                    Collision.setPeopleKilled(Integer.parseInt(updateValue));
                    break;
                case "pedestrians injured":
                    updateStmt.setInt(1, Integer.parseInt(updateValue));
                    Collision.setPedestriansInjured(Integer.parseInt(updateValue));
                    break;
                case "pedestrians killed":
                    updateStmt.setInt(1, Integer.parseInt(updateValue));
                    Collision.setPedestriansKilled(Integer.parseInt(updateValue));
                    break;
                case "cyclists injured":
                    updateStmt.setInt(1, Integer.parseInt(updateValue));
                    Collision.setCyclistsInjured(Integer.parseInt(updateValue));
                    break;
                case "cyclists killed":
                    updateStmt.setInt(1, Integer.parseInt(updateValue));
                    Collision.setCyclistsKilled(Integer.parseInt(updateValue));
                    break;
                case "motorists injured":
                    updateStmt.setInt(1, Integer.parseInt(updateValue));
                    Collision.setMotoristsInjured(Integer.parseInt(updateValue));
                    break;
                case "motorists killed":
                    updateStmt.setInt(1, Integer.parseInt(updateValue));
                    Collision.setMotoristsKilled(Integer.parseInt(updateValue));
                    break;
                default:
                    throw new SQLException("Column [" + columnName + "] not found");
            }
            updateStmt.executeUpdate();
            return Collision;
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
     * Deletes the Collision from the database
     *
     * @param Collision
     * @return
     * @throws SQLException
     */
    public Collision delete(Collision Collision) throws SQLException {
        String deleteCollision = "DELETE FROM Collision WHERE Collisionpk=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteCollision);
            deleteStmt.setLong(1, Collision.getKey());
            deleteStmt.executeUpdate();

            // Return null so the caller can no longer operate on the Collisions instance.
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

    /**
	 * Get the all the Collisions for a borough.
	 */
	public List<Collision> getCollisionsByBorough(String borough) throws SQLException {
		List<Collision> collisions = new ArrayList<Collision>();
		String selectCollisions =
            "SELECT CollisionPK, Date, Borough, ZipCode, `PERSONS INJURED`, `PERSONS KILLED`," +
            " `PEDESTRIANS INJURED`, `PEDESTRIANS KILLED`, `CYCLISTS INJURED`," +
            " `CYCLISTS KILLED`, `MOTORISTS INJURED`, `MOTORISTS KILLED`, Latitude, Longitude " +
            "FROM Collision " +
            "JOIN destination d on Collision.CollisionPK = d.DestinationPK " +
            "WHERE Borough=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCollisions);
			selectStmt.setString(1, borough);
			results = selectStmt.executeQuery();
			while(results.next()) {
				long CollisionKey = results.getLong("CollisionPK");
                Date date = results.getDate("Date");
                float lat = results.getFloat("latitude");
                float lng = results.getFloat("longitude");

                int zipCode = results.getInt("ZipCode");
                String resultBorough = results.getString("Borough");

                int persons_injured = results.getInt("PERSONS INJURED");
                int persons_killed = results.getInt("PERSONS KILLED");
                int pedestrians_injured = results.getInt("PEDESTRIANS INJURED");
                int pedestrians_killed = results.getInt("PEDESTRIANS KILLED");
                int cyclists_injured = results.getInt("CYCLISTS INJURED");
                int cyclists_killed = results.getInt("CYCLISTS KILLED");
                int motorists_injured = results.getInt("MOTORISTS INJURED");
                int motorists_killed = results.getInt("MOTORISTS KILLED");

                Collision collision = new Collision(CollisionKey, lat, lng, date, resultBorough, zipCode, persons_injured, persons_killed, pedestrians_injured, pedestrians_killed, cyclists_injured, cyclists_killed, motorists_injured, motorists_killed);
                collisions.add(collision);
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
		return collisions;
	}
}
