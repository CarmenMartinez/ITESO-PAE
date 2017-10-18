package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import utils.Utils;

public class CreateAccountController implements Initializable {

	@FXML private TextField textFieldName, textFieldPassword;

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML public void createAccount (ActionEvent event) {
		/*
		// SAVE THE NEW ACCOUNT IN THE DATA BASE
		*/
		Utils.createWindow(null, CreateAccountController.this, "../fxml/Home.fxml", "TasksFX", null, "../css/home.css");
		Utils.closeWindow(event);
	}

}
