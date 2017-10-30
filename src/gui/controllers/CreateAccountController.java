package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import interfaces.RunnableTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import model.User;
import utils.ThreadHandler;
import utils.ThreadHandler.ThreadHandlerException;
import utils.Utils;

public class CreateAccountController implements Initializable {

	@FXML private TextField textFieldFirstName, textFieldLastName, textFieldEmail, textFieldPassword, textFieldUserName;

	public void initialize(URL arg0, ResourceBundle arg1) {}

	@FXML public void createAccount (ActionEvent event) {
		if (!isValidData()) {
			Utils.showError("No puedes dejar campos vacios.");
			return;
		}

		try {
			ThreadHandler.getInstance().setRunnableTask(new RunnableTask() {

				@Override
				public void onFinish(Object response) {
					if (response == null) {
						Utils.showError("Este nombre de usuario ya existe, por favor elige otro.");
						return;
					}
					User user = (User) response;
					Utils.createWindow(null, CreateAccountController.this, "../fxml/Home.fxml", "TasksFX", user, "../css/home.css", "resources.i18n.home");
					Utils.closeWindow(event);
				}

			}).performCreateUser(textFieldFirstName.getText(), textFieldLastName.getText(), textFieldUserName.getText(), textFieldEmail.getText(), textFieldPassword.getText());
		} catch (InterruptedException | ExecutionException | ThreadHandlerException e) {
			e.printStackTrace();
		}

	}

	private boolean isValidData() {
		return Utils.isValidInput(textFieldFirstName.getText()) &&
				Utils.isValidInput(textFieldLastName.getText()) &&
				Utils.isValidInput(textFieldEmail.getText()) &&
				Utils.isValidInput(textFieldUserName.getText()) &&
				Utils.isValidInput(textFieldPassword.getText());

	}

}
