package gui.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import db.DBHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.Folder;
import model.User;
import utils.Utils;

public class LoginController implements Initializable {

	@FXML private TextField textFieldName, textFieldPassword;

	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			DBHandler.initConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML public void login(ActionEvent event) {
		String userName = textFieldName.getText();
		String userPassword = textFieldPassword.getText();
		if (!Utils.isValidInput(userName) || !Utils.isValidInput(userPassword)) {
			Utils.showError("Por favor ingresa el nombre de usuario y contrasenia.");
			return;
		}
		User user = null;
		try {
			user = DBHandler.login(userName, userPassword);
			if (user == null) {
				Utils.showError("Nombre de usuario o contrasenia incorrectos. Por favor revisa tus datos.");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			user.setFolders(DBHandler.getUserFolders(user.getId()));
			Folder folder = user.getFolders().get(0);
			folder.setTasks(DBHandler.getFolderTasks(folder.getId()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Utils.createWindow(null, LoginController.this, "../fxml/Home.fxml", "TasksFX", user, "../css/home.css", "resources.i18n.home");
		Utils.closeWindow(event);
	}

	@FXML public void createAccount(ActionEvent event) {
		Utils.createWindow(null, LoginController.this, "../fxml/CreateAccount.fxml", "TasksFX", null, "../css/create-account.css", "resources.i18n.createaccount");
		Utils.closeWindow(event);
	}

}
