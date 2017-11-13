package main.java.model;

import main.java.interfaces.TasksHandler;

public class TaskManager {

	private TasksHandler taskHandler;
	private Task task;
	private int folderId;

	public TasksHandler getTaskHandler() {
		return taskHandler;
	}

	public TaskManager setTaskHandler(TasksHandler taskHandler) {
		this.taskHandler = taskHandler;
		return this;
	}

	public Task getTask() {
		return task;
	}

	public TaskManager setTask(Task task) {
		this.task = task;
		return this;
	}

	public int getFolderId() {
		return folderId;
	}

	public TaskManager setFolderId(int folderId) {
		this.folderId = folderId;
		return this;
	}

}
