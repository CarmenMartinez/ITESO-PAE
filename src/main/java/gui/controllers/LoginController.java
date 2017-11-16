package main.java.gui.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import main.java.db.DBHandler;
import main.java.interfaces.RunnableTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.model.User;
import main.java.utils.ThreadHandler;
import main.java.utils.ThreadHandler.ThreadHandlerException;
import main.java.utils.Utils;

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
		ResourceBundle bundle = ResourceBundle.getBundle("i18n/login");
		String userName = textFieldName.getText();
		//PasswordField userPassword = new P
		String userPassword = textFieldPassword.getText();
		if (!Utils.isValidInput(userName) || !Utils.isValidInput(userPassword)) {
			Utils.showError(bundle.getString("invalid_input"));
			return;
		}
		try {
			ThreadHandler.getInstance().setRunnableTask(new RunnableTask() {

				@Override
				public void onFinish(Object response) {
					if (response == null) {
						Utils.showError(bundle.getString("incorrect"));
						return;
					}
					User user = (User) response;
					Utils.createWindow(null, LoginController.this, "fxml/Home.fxml", "TasksFX", user, "css/home.css", "i18n/home");
					Utils.closeWindow(event);
				}

			}).performLogin(userName, userPassword);
		} catch (InterruptedException | ExecutionException | ThreadHandlerException e) {
			e.printStackTrace();
		}
	}

	@FXML public void createAccount(ActionEvent event) {
		Utils.createWindow(null, LoginController.this, "fxml/CreateAccount.fxml", "TasksFX", null, "css/create-account.css", "i18n/createaccount");
		Utils.closeWindow(event);
	}

}
