package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TaskController implements Initializable {

	@FXML private TextField textFieldTitle, textFieldDate;

	@FXML private TextArea textAreaBody, textAreaNote;


	public void initialize(URL location, ResourceBundle resources) {

	}

}
