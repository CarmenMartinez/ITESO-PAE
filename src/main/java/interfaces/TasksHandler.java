package main.java.interfaces;

import main.java.model.Task;

public interface TasksHandler {

	public void addTask(Task task);
	public void updateTask(Task task);
	public void closeTask(Task task);
	public void onStatusChanged(Task task, boolean status);

}
