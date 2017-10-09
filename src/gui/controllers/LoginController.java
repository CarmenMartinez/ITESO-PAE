package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import utils.Utils;

public class LoginController implements Initializable {

	@FXML private TextField textFieldName, textFieldPassword;

	public void initialize(URL arg0, ResourceBundle arg1) {


	}

	@FXML public void login(ActionEvent event) {
		Utils.createWindow(null, LoginController.this, "../fxml/Home.fxml", "TasksFX", null, "../css/home.css", "resources.i18n.login");
		Utils.closeWindow(event);
	}


}
