package gui.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import db.DBHandler;
import interfaces.RunnableTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.User;
import utils.ThreadHandler;
import utils.ThreadHandler.ThreadHandlerException;
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
		try {
			ThreadHandler.getInstance().setRunnableTask(new RunnableTask() {

				@Override
				public void onFinish(Object response) {
					if (response == null) {
						Utils.showError("Nombre de usuario o contrasenia incorrectos. Por favor revisa tus datos.");
						return;
					}
					User user = (User) response;
					Utils.createWindow(null, LoginController.this, "../fxml/Home.fxml", "TasksFX", user, "../css/home.css", "resources.i18n.home");
					Utils.closeWindow(event);
				}

			}).performLogin(userName, userPassword);
		} catch (InterruptedException | ExecutionException | ThreadHandlerException e) {
			e.printStackTrace();
		}
	}

	@FXML public void createAccount(ActionEvent event) {
		Utils.createWindow(null, LoginController.this, "../fxml/CreateAccount.fxml", "TasksFX", null, "../css/create-account.css", "resources.i18n.createaccount");
		Utils.closeWindow(event);
	}

}
