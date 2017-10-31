package gui.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import interfaces.RunnableTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import model.Folder;
import model.FolderManager;
import utils.ThreadHandler;
import utils.ThreadHandler.ThreadHandlerException;
import utils.Utils;

public class FolderController implements Initializable {

	@FXML private TextField textFieldFolderName;

	@FXML private CheckBox checkBoxFavorite;

	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML public void accept(ActionEvent event) {
		Object userData = textFieldFolderName.getScene().getWindow().getUserData();
		if (!isValidData() || userData == null || !(userData instanceof FolderManager)) {
			return;
		}
		FolderManager folderManager = (FolderManager) userData;
		try {
			ThreadHandler.getInstance().setRunnableTask(new RunnableTask() {

				@Override
				public void onFinish(Object response) {
					if (response == null) {
						Utils.showError("Unable to create folder.");
						return;
					}
					Folder folder = (Folder) response;
					folderManager.getFolderHandler().addFolder(folder);
					Utils.closeWindow(event);
				}

			}).performInsertFolder(folderManager.getUser().getId(), textFieldFolderName.getText(), checkBoxFavorite.isSelected(), "Activa", LocalDateTime.now());
		} catch (InterruptedException | ExecutionException | ThreadHandlerException e) {
			e.printStackTrace();
		}

	}

	@FXML public void cancel(ActionEvent event) {
		Utils.closeWindow(event);
	}

	private boolean isValidData() {
		return textFieldFolderName.getText().length() > 0;
	}

}
