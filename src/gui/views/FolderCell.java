package gui.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import model.Folder;

public class FolderCell extends ListCell<Folder> {

	@Override
    public void updateItem(final Folder folder, boolean empty) {
        super.updateItem(folder, empty);

        if (folder == null) { return; }

        final ImageView imageView = new ImageView(
        	      new Image("http://www.iconsdb.com/icons/preview/gray/folder-xxl.png")
        	    );
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        Button button = new Button(folder.getName(),imageView);
        // Add class name to this button to apply styles from the CSS file.
        button.getStyleClass().add("folder");
        button.setAlignment(Pos.BASELINE_LEFT);
        button.setId("folder");
        button.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText("Seleccionaste el folder: " + folder.getName());
				alert.show();
			}
		});

        setGraphic(button);
    }

}
