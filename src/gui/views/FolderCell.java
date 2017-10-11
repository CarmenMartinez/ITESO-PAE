package gui.views;

import interfaces.FolderHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import model.Folder;

public class FolderCell extends ListCell<Folder> {

	private FolderHandler folderHandler;

	public FolderCell(FolderHandler folderHandler) {
		this.folderHandler = folderHandler;
	}

	@Override
    public void updateItem(final Folder folder, boolean empty) {
        super.updateItem(folder, empty);

        if (folder == null) {
        	setGraphic(null);
        	return;
        }

        final ImageView imageView = new ImageView(
        	      new Image("http://www.iconsdb.com/icons/preview/gray/folder-xxl.png")
        	    );
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        Button button = new Button(folder.getName(), imageView);
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem item1 = new MenuItem("Eliminar");
        item1.setOnAction(e -> { folderHandler.onFolderDeleted(folder); });
        contextMenu.getItems().add(item1);
        button.setContextMenu(contextMenu);
        // Add class name to this button to apply styles from the CSS file.
        button.getStyleClass().add("folder");
        button.setAlignment(Pos.BASELINE_LEFT);
        button.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				folderHandler.onFolderSelected(folder);
			}
		});

        setGraphic(button);
    }

}
