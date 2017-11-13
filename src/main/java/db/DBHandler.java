package main.java.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.model.Folder;
import main.java.model.Task;
import main.java.model.User;
import main.java.utils.Utils;

public class DBHandler {

	public static final int SOCKET_PORT = 5555;
	public static final String SOCKET_HOST = "localhost";

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    /**
     * Function that creates a new connection to the database.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void initConnection() throws ClassNotFoundException, SQLException {
        Properties prop = new Properties();
        try(InputStream input = new FileInputStream("db.properties")) {
            prop.load(input);
            String db = prop.getProperty("db");
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
        if (statement != null) {
            statement.close();
        }
        statement = connection.createStatement();
        return statement;
    }

    public static void login(String userName, String userPassword) throws SQLException {
        // Select Customers query.
        String query = "SELECT * FROM Person WHERE (userName = '" + userName + "' OR email = '" + userName + "') AND password = '" + userPassword + "'";
        // Execute query
        ResultSet resultSet = executeQuery(query);
        User user = resultSet.next()
        		? new User(
		                resultSet.getInt("id"),
		                resultSet.getString("name"),
		                resultSet.getString("lastName"),
		                resultSet.getString("userName"),
		                resultSet.getString("email")
		          )
        		: null;
        writeUserToSocket(user);
    }

    private static void writeUserToSocket(User user) {
    	Socket socket;

		try {
			socket = new Socket(SOCKET_HOST, SOCKET_PORT);

			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			oos.writeObject(user);
			oos.flush();
			oos.close();
			ois.close();
			socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static User createUser(String name, String lastName, String userName, String email, String password) throws SQLException {
    	String queryUsers = "SELECT * FROM Person WHERE userName = '" + userName + "'";
    	ResultSet resultSetUsers = executeQuery(queryUsers);
    	boolean repeatedUser = resultSetUsers.next();
    	resultSetUsers.close();
    	if (repeatedUser) return null;
    	// Create the sql statement.
        String query = "INSERT INTO Person(name, lastName, userName, email, password) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = getPreparedStatement(query);
        // Bind all the data
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, userName);
        preparedStatement.setString(4, email);
        preparedStatement.setString(5, password);
        // Execute and close.
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        User user = resultSet.next() ? new User(resultSet.getInt(1), name, lastName, userName, email) : null;
        resultSet.close();
        preparedStatement.close();
        return user;
    }

    public static ObservableList<Folder> getUserFolders(int userId) throws SQLException {
        ObservableList<Folder> folders = FXCollections.observableArrayList();
        // Select Customers query.
        String query = "SELECT * FROM Folder WHERE userId = " + userId + " ORDER BY isFavorite DESC, name ASC";
        // Execute query
        ResultSet resultSet = executeQuery(query);
        DateTimeFormatter formatter = Utils.getDBDateTimeFormatter();
        String creationDate;
        while (resultSet.next()) {
        	creationDate = resultSet.getString("creationDate");
            Folder folder = new Folder(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("isFavorite") == 1,
                resultSet.getString("status"),
                creationDate != null ? LocalDateTime.parse(Utils.removeNanos(creationDate), formatter) : null
            );
            folders.add(folder);
        }
        return folders;
    }

    public static ObservableList<Task> getFolderTasks(int folderId) throws SQLException {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        // Select Customers query.
        String query = "SELECT * FROM Task WHERE folderId = " + folderId;
        // Execute query
        ResultSet resultSet = executeQuery(query);
        DateTimeFormatter formatter = Utils.getDBDateTimeFormatter();
        String creationDate, reminderDate;
        while (resultSet.next()) {
        	creationDate = resultSet.getString("creationDate");
        	reminderDate = resultSet.getString("reminderDate");
            Task task = new Task(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getDouble("xPosition"),
                resultSet.getDouble("yPosition"),
                resultSet.getDouble("width"),
                resultSet.getDouble("height"),
                resultSet.getString("color"),
                resultSet.getString("status"),
                creationDate != null ? LocalDateTime.parse(Utils.removeNanos(creationDate), formatter) : null,
                reminderDate != null ? LocalDateTime.parse(Utils.removeNanos(reminderDate), formatter) : null
            );
            tasks.add(task);
        }
        return tasks;
    }

    public static Folder insertFolder(int userId, String name, boolean isFavorite, String status, LocalDateTime creationDate) throws SQLException {
        // Create the sql statement.
        String query = "INSERT INTO Folder(userId, name, isFavorite, status, creationDate) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = getPreparedStatement(query);
        // Bind all the data
        preparedStatement.setInt(1, userId);
        preparedStatement.setString(2, name);
        preparedStatement.setInt(3, isFavorite ? 1 : 0);
        preparedStatement.setString(4, status);
        preparedStatement.setTimestamp(5, creationDate != null ? new Timestamp(creationDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) : null);

        // Execute and close.
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        Folder folder = resultSet.next() ? new Folder(resultSet.getInt(1), name, isFavorite, status, creationDate) : null;
        resultSet.close();
        preparedStatement.close();
        return folder;
    }

    public static Task insertTask(int folderId, String title, String description, String color, double width, double height, double xPosition, double yPosition, String status, LocalDateTime creationDate, LocalDateTime reminderDate) throws SQLException {
        // Create the sql statement.
        String query = "INSERT INTO Task(folderId, title, description, color, width, height, xPosition, yPosition, status, creationDate, reminderDate) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = getPreparedStatement(query);
        // Bind all the data
        preparedStatement.setInt(1, folderId);
        preparedStatement.setString(2, title);
        preparedStatement.setString(3, description);
        preparedStatement.setString(4, color);
        preparedStatement.setDouble(5, width);
        preparedStatement.setDouble(6, height);
        preparedStatement.setDouble(7, xPosition);
        preparedStatement.setDouble(8, yPosition);
        preparedStatement.setString(9, status);
        preparedStatement.setTimestamp(10, creationDate != null ? new Timestamp(creationDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) : null);
        preparedStatement.setTimestamp(11, reminderDate != null ? new Timestamp(reminderDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) : null);

        // Execute and close.
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        Task task = resultSet.next() ? new Task(resultSet.getInt(1), title, description, xPosition, yPosition, width, height, color, status, creationDate, reminderDate) : null;
        resultSet.close();
        preparedStatement.close();
        return task;
    }

    public static Folder deleteFolder(Folder folder) throws SQLException {
    	String queryDeleteFolder = "DELETE FROM Folder WHERE id = " + folder.getId();
    	String queryDeleteFolderTasks = "DELETE FROM Task WHERE folderId = " + folder.getId();
    	executeUpdateQuery(queryDeleteFolderTasks);
    	executeUpdateQuery(queryDeleteFolder);
    	return folder;
    }

    public static Task deleteTask(Task task) throws SQLException {
    	String query = "DELETE FROM Task WHERE id = " + task.getId();
    	return executeUpdateQuery(query) > 0 ? task : null;
    }

    public static Task updateTaskInfo(Task task) throws SQLException {
    	String query = "UPDATE Task SET title = ?, description = ?, reminderDate = ? WHERE id = " + task.getId();
    	PreparedStatement preparedStatement = getPreparedStatement(query);
    	preparedStatement.setString(1, task.getTitle());
    	preparedStatement.setString(2, task.getDescription());
    	preparedStatement.setTimestamp(3, task.getReminderDate() != null ? new Timestamp(task.getReminderDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) : null);
    	// Execute and close.
        preparedStatement.execute();
        preparedStatement.close();
        return task;
    }

    public static Task updateTaskAttributes(Task task) throws SQLException {
    	String query = "UPDATE Task SET width = ?, height = ?, xPosition = ?, yPosition = ? WHERE id = " + task.getId();;
    	PreparedStatement preparedStatement = getPreparedStatement(query);
    	preparedStatement.setDouble(1, task.getWidth());
        preparedStatement.setDouble(2, task.getHeight());
        preparedStatement.setDouble(3, task.getXPosition());
        preparedStatement.setDouble(4, task.getYPosition());
        // Execute and close.
        preparedStatement.execute();
        preparedStatement.close();
        return task;
    }

}
