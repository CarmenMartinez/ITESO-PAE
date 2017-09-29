package gui.controllers;

import interfaces.WindowState;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Task;
import model.TaskManager;
import utils.Utils;

public class TaskController implements WindowState {

	@FXML private TextField textFieldTitle, textFieldDate;

	@FXML private TextArea textAreaBody, textAreaNote;

	private TaskManager taskManager;

	@Override
	public void onReady() {
		Object userData = textFieldTitle.getScene().getWindow().getUserData();
		if (userData != null && userData instanceof TaskManager) {
			taskManager = ((TaskManager) userData);
		}
		Task task = taskManager.getTask();
		if (task != null) {
			textFieldTitle.setText(task.getTitle());
			textAreaBody.setText(task.getDescription());
		}
	}

	@FXML public void accept(ActionEvent event) {

		if (isValidData()) {
			try {
				if (taskManager.getTask() == null) {
					taskManager.getTaskHandler().addTask(new Task(textFieldTitle.getText(), textAreaBody.getText()));
				} else {
					Task task = taskManager.getTask();
					task.setTitle(textFieldTitle.getText());
					task.setDescription(textAreaBody.getText());
					taskManager.getTaskHandler().updateTask(task);
				}
				Utils.closeWindow(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML public void cancel(ActionEvent event) {
		Utils.closeWindow(event);
	}

	private boolean isValidData() {
		return (textFieldTitle.getText().length() > 0) && (textAreaBody.getText().length() > 0);
	}

}
