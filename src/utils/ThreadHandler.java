package utils;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import db.DBHandler;
import interfaces.RunnableTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Folder;
import model.Task;
import model.User;

public class ThreadHandler {

	private static ThreadHandler threadHandler;

	private ExecutorService executorService;

	private RunnableTask runnableTask;

	private ThreadHandler() {
		executorService = Executors.newSingleThreadExecutor();
	}

	public static ThreadHandler getInstance() {
		if (threadHandler == null) {
			threadHandler = new ThreadHandler();
		}
		return threadHandler;
	}

	public void shutdown() {
		executorService.shutdown();
		threadHandler = null;
	}

	public ThreadHandler setRunnableTask(RunnableTask runnableTask) {
		this.runnableTask = runnableTask;
		return threadHandler;
	}

	private void callRunnableTask(Object response) {
		runnableTask.onFinish(response);
		runnableTask = null;
	}

	private void checkRunnableTask() throws ThreadHandlerException {
		if (runnableTask == null) {
			throw new ThreadHandlerException("You need to specify a RunnableTask before performing an action.");
		}
	}

	private void performAction(Callable<?> callable) throws InterruptedException, ExecutionException, ThreadHandlerException {
		checkRunnableTask();
		Future<?> future = executorService.submit(callable);
		callRunnableTask(future.get());
	}

	public void performLogin(String... args) throws InterruptedException, ExecutionException, ThreadHandlerException {
		performAction(new Callable<User>() {

			@Override
			public User call() throws Exception {
				User user = null;
				try {
					user = DBHandler.login(args[0], args[1]);
					if (user == null) {
						return null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					user.setFolders(DBHandler.getUserFolders(user.getId()));
					if (user.getFolders().size() > 0) {
						Folder folder = user.getFolders().get(0);
						folder.setTasks(DBHandler.getFolderTasks(folder.getId()));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return user;
			}

		});
	}

	public void performCreateUser(String... args) throws InterruptedException, ExecutionException, ThreadHandlerException {
		performAction(new Callable<User>() {

			@Override
			public User call() throws Exception {
				User user = null;
				try {
					user = DBHandler.createUser(args[0], args[1], args[2], args[3], args[4]);
					if (user == null) {
						return null;
					}
					user.setFolders(FXCollections.observableArrayList());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return user;
			}
		});
	}

	public void performGetFolderTasks(int folderId) throws InterruptedException, ExecutionException, ThreadHandlerException {
		performAction(new Callable<ObservableList<Task>>() {

			@Override
			public ObservableList<Task> call() throws Exception {
				return DBHandler.getFolderTasks(folderId);
			}

		});
	}

	public void performDeleteFolder(Folder folder) throws InterruptedException, ExecutionException, ThreadHandlerException {
		performAction(new Callable<Folder>() {

			@Override
			public Folder call() throws Exception {
				return DBHandler.deleteFolder(folder);
			}

		});
	}

	public void performUpdateTaskAttributes(Task task) throws InterruptedException, ExecutionException, ThreadHandlerException {
		performAction(new Callable<Task>() {

			@Override
			public Task call() throws Exception {
				return DBHandler.updateTaskAttributes(task);
			}

		});
	}

	public void performDeleteTask(Task task) throws InterruptedException, ExecutionException, ThreadHandlerException {
		performAction(new Callable<Task>() {

			@Override
			public Task call() throws Exception {
				return DBHandler.deleteTask(task);
			}

		});
	}

	public void performInsertFolder(int userId, String folderName, boolean isFavorite, String status, LocalDateTime creationDate) throws InterruptedException, ExecutionException, ThreadHandlerException {
		performAction(new Callable<Folder>() {

			@Override
			public Folder call() throws Exception {
				return DBHandler.insertFolder(userId, folderName, isFavorite, status, creationDate);
			}

		});
	}

	public void performInsertTask(int folderId, String title, String description, String color, double width, double height, double xPosition, double yPosition, String status, LocalDateTime creationDate, LocalDateTime reminderDate) throws InterruptedException, ExecutionException, ThreadHandlerException {
		performAction(new Callable<Task>() {

			@Override
			public Task call() throws Exception {
				return DBHandler.insertTask(folderId, title, description, color, width, height, xPosition, yPosition, status, creationDate, reminderDate);
			}

		});
	}

	public void performUpdateTaskInfo(Task task) throws InterruptedException, ExecutionException, ThreadHandlerException {
		performAction(new Callable<Task>() {

			@Override
			public Task call() throws Exception {
				return DBHandler.updateTaskInfo(task);
			}

		});
	}

	public class ThreadHandlerException extends Exception {

		private static final long serialVersionUID = -1864451953976204004L;

		public ThreadHandlerException(String message) {
			super(message);
		}
	}

}
