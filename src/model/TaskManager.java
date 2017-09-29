package model;

import interfaces.TasksHandler;

public class TaskManager {

	private TasksHandler taskHandler;
	private Task task;

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

}
