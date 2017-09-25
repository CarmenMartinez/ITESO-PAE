package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import interfaces.TasksHandler;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Task;
import utils.Utils;

public class TaskController implements Initializable {

	@FXML private TextField textFieldTitle, textFieldDate;

	@FXML private TextArea textAreaBody, textAreaNote;


	public void initialize(URL location, ResourceBundle resources) {

	}
	
	@FXML public void accept(ActionEvent event) {
		Object userData = textFieldTitle.getScene().getWindow().getUserData();
		if (isValidData() && userData != null && userData instanceof TasksHandler) {
			try {
				((TasksHandler) userData).addTask(new Task(textFieldTitle.getText(),textAreaBody.getText()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Utils.closeWindow(event);
	}

	@FXML public void cancel(ActionEvent event) {
		Utils.closeWindow(event);
	}
	
	private boolean isValidData() {
		return textFieldTitle.getText().length() > 0;
	}

}
