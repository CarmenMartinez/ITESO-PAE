package main.java.gui.controllers;

import java.time.LocalDateTime;
import java.util.ResourceBundle;

import main.java.interfaces.RunnableTask;
import main.java.interfaces.WindowState;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jfxtras.scene.control.LocalDateTimeTextField;
import main.java.model.Task;
import main.java.model.TaskManager;
import main.java.utils.ThreadHandler;
import main.java.utils.Utils;

public class TaskController implements WindowState {

	@FXML private TextField textTextFieldTitle, reminderTextFieldTitle;

	@FXML private LocalDateTimeTextField reminderTextFieldDate;

	@FXML private TextArea textTextAreaBody, reminderTextAreaBody;

	@FXML private TabPane tabPane;

	@FXML private ColorPicker reminderColorPicker, textColorPicker;

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
					reminderTextFieldDate.setLocalDateTime(task.getReminderDate());
					break;
				default:
					break;
			}
		}
	}

	@FXML public void accept(ActionEvent event) {
		ResourceBundle bundle = ResourceBundle.getBundle("i18n/task");
		if (!isValidData()) {
			return;
		}
		try {
			int selectedTab = tabPane.getSelectionModel().getSelectedIndex();
			String title = null, description = null, color = "#";
			switch (selectedTab) {
				case 0:
					title = textTextFieldTitle.getText();
					description = textTextAreaBody.getText();
					color = color + textColorPicker.getValue().toString().substring(2);
					break;
				case 1:
					title = reminderTextFieldTitle.getText();
					description = reminderTextAreaBody.getText();
					color = color + reminderColorPicker.getValue().toString().substring(2);
					break;
				default:
					break;
			}
			boolean isReminder = selectedTab == 1;
			if (taskManager.getTask() == null) {
				ThreadHandler.getInstance().setRunnableTask(new RunnableTask() {

					@Override
					public void onFinish(Object response) {
						if (response == null) {
							Utils.showError(bundle.getString("error_insert"));
							return;
						}
						Task task = (Task) response;
						taskManager.getTaskHandler().addTask(task);
					}

				}).performInsertTask(taskManager.getFolderId(), title, description, color, Task.DEFAULT_WIDTH, Task.DEFAULT_HEIGHT, Task.DEFAULT_POSITION, Task.DEFAULT_POSITION, bundle.getString("task_pending"), LocalDateTime.now(), isReminder ? reminderTextFieldDate.getLocalDateTime() : null);
			} else {
				Task task = taskManager.getTask();
				task.setTitle(title);
				task.setDescription(description);
				task.setColor(color);
				if (isReminder) {
					task.setReminderDate(reminderTextFieldDate.getLocalDateTime());
				}
				ThreadHandler.getInstance().setRunnableTask(new RunnableTask() {

					@Override
					public void onFinish(Object response) {
						if (response == null) {
							Utils.showError(bundle.getString("error_update"));
							return;
						}
						taskManager.getTaskHandler().updateTask(task);
					}

				}).performUpdateTaskInfo(task);
			}
			Utils.closeWindow(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML public void cancel(ActionEvent event) {
		Utils.closeWindow(event);
	}

	@FXML public void change(ActionEvent event){

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
