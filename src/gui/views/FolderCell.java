package gui.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.Alert.AlertType;
import model.Folder;

public class FolderCell extends ListCell<Folder> {

	@Override
    public void updateItem(Folder folder, boolean empty) {
        super.updateItem(folder, empty);

        if (folder == null) { return; }

        Button button = new Button(folder.getName());
        // Add class name to this button to apply styles from the CSS file.
        button.getStyleClass().add("folder");
        button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText("Seleccionaste el folder: " + folder.getName());
				alert.show();
			}
		});

        setGraphic(button);
    }

}
