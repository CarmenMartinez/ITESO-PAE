package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHandler {

	private static Connection connection;
	private static Statement statement;
	private static ResultSet resultSet;

	/**
	 * Function that creates a new connection to the database.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void initConnection() throws ClassNotFoundException, SQLException {
		String db = "jdbc:derby:/Users/Alexis/MyDB;create=true";
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		connection = DriverManager.getConnection(db);
	}

	/**
	 * Function that executes an update query (insert, delete or update)
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public static int executeUpdateQuery(String query) throws SQLException {
		return getStatement().executeUpdate(query);
	}

	/**
	 * Function that executes a simple SELECT query.
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet executeQuery(String query) throws SQLException {
		if (resultSet != null) {
			resultSet.close();
		}
		resultSet = getStatement().executeQuery(query);
		return resultSet;
	}

	/**
	 * Function that gets a PreparedStatement with the given sql statement.
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
		return connection.prepareStatement(sql);
	}

	/**
	 * Function that attemps to close all existing connections
	 * @throws SQLException
	 */
	public static void closeConnections() throws SQLException {
		if (resultSet != null) {
			resultSet.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (connection != null) {
			connection.close();
		}
	}

	/**
	 * Function that gets a statement.
	 * @return Statement
	 * @throws SQLException
	 */
	private static Statement getStatement() throws SQLException {
		if (statement == null) {
			statement = connection.createStatement();
		} else {
			statement.close();
		}
		return statement;
	}

}
