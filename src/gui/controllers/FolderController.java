package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import utils.Utils;

public class FolderController implements Initializable {

	@FXML private TextField textFieldFolderName;

	@FXML private CheckBox checkBoxFavorite;

	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML public void accept(ActionEvent event) {
		Utils.closeWindow(event);
	}

	@FXML public void cancel(ActionEvent event) {
		Utils.closeWindow(event);
	}

}
