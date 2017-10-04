package gui.controllers;

import java.time.LocalDateTime;

import interfaces.WindowState;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Task;
import model.TaskManager;
import utils.Utils;

public class TaskController implements WindowState {

	@FXML private TextField textTextFieldTitle, reminderTextFieldTitle, reminderTextFieldDate;

	@FXML private TextArea textTextAreaBody, reminderTextAreaBody;

	@FXML private TabPane tabPane;

	private TaskManager taskManager;

	@Override
	public void onReady() {
		Object userData = tabPane.getScene().getWindow().getUserData();
		if (userData != null && userData instanceof TaskManager) {
			taskManager = ((TaskManager) userData);
		}
		Task task = taskManager.getTask();
		if (task != null) {
			if (task.isReminder()) {
				tabPane.getSelectionModel().select(1);
			}
			int selectedTab = tabPane.getSelectionModel().getSelectedIndex();
			switch (selectedTab) {
				case 0:
					textTextFieldTitle.setText(task.getTitle());
					textTextAreaBody.setText(task.getDescription());
					break;
				case 1:
					reminderTextFieldTitle.setText(task.getTitle());
					reminderTextAreaBody.setText(task.getDescription());
					reminderTextFieldDate.setText(task.getReminderDate().toString());
					break;
				default:
					break;
			}
		}
	}

	@FXML public void accept(ActionEvent event) {

		if (isValidData()) {
			try {
				int selectedTab = tabPane.getSelectionModel().getSelectedIndex();
				String title = null, description = null;
				switch (selectedTab) {
					case 0:
						title = textTextFieldTitle.getText();
						description = textTextAreaBody.getText();
						break;
					case 1:
						title = reminderTextFieldTitle.getText();
						description = reminderTextAreaBody.getText();
						break;
					default:
						break;
				}
				boolean isReminder = selectedTab == 1;
				if (taskManager.getTask() == null) {
					taskManager.getTaskHandler().addTask(new Task(title, description, isReminder ? LocalDateTime.now() : null));
				} else {
					Task task = taskManager.getTask();
					task.setTitle(title);
					task.setDescription(description);
					if (isReminder) {
						task.setReminderDate(LocalDateTime.now());
					}
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
		int selectedTab = tabPane.getSelectionModel().getSelectedIndex();
		switch (selectedTab) {
			case 0:
				return (textTextFieldTitle.getText().length() > 0) && (textTextAreaBody.getText().length() > 0);
			case 1:
				return (reminderTextFieldTitle.getText().length() > 0) && (reminderTextAreaBody.getText().length() > 0);
			default:
				return false;
		}
	}

}
