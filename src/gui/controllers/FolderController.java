package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import interfaces.FolderHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import model.Folder;
import utils.Utils;

public class FolderController implements Initializable {

	@FXML private TextField textFieldFolderName;

	@FXML private CheckBox checkBoxFavorite;

	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML public void accept(ActionEvent event) {
		Object userData = textFieldFolderName.getScene().getWindow().getUserData();
		if (isValidData() && userData != null && userData instanceof FolderHandler) {
			try {
				((FolderHandler) userData).addFolder(new Folder((int) Math.floor(Math.random() * 100000), textFieldFolderName.getText()));
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
		return textFieldFolderName.getText().length() > 0;
	}

}
