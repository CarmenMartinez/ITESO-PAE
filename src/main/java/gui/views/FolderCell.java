package main.java.gui.views;

import main.java.interfaces.FolderHandler;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.model.Folder;

public class FolderCell extends ListCell<Folder> {

	private FolderHandler folderHandler;
    private ResourceBundle bundle;


	public FolderCell(FolderHandler folderHandler) {
		this.folderHandler = folderHandler;
        bundle = ResourceBundle.getBundle("i18n/folder");
	}

	@Override
    public void updateItem(final Folder folder, boolean empty) {
        super.updateItem(folder, empty);

        if (folder == null || empty) {
        	setGraphic(null);
        	return;
        }

        final ImageView imageView = new ImageView(new Image((folder.isFavorite() ? "img/favoriteFolder.png" : "img/folder.png")));
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        Button button = new Button(folder.getName(), imageView);
        folder.button = button;
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem item1 = new MenuItem(bundle.getString("delete"));
        final MenuItem item2 = new MenuItem(bundle.getString("remove"));
        final MenuItem item3 = new MenuItem(bundle.getString("add"));

        item2.setOnAction(e -> {folderHandler.onFolderRemoved(folder);});
        item3.setOnAction(e -> {folderHandler.onFolderAdded(folder);});

        item1.setOnAction(e -> { folderHandler.onFolderDeleted(folder); });
        contextMenu.getItems().add(item1);

        if (folder.isFavorite()) {
        	contextMenu.getItems().add(item2);
        } else {
        	contextMenu.getItems().add(item3);
        }

        button.setContextMenu(contextMenu);
        // Add class name to this button to apply styles from the CSS file.
        button.getStyleClass().add("folder");
        button.setAlignment(Pos.BASELINE_LEFT);


        button.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				folderHandler.onFolderSelected(folder);



				folderHandler.onFolderSelectedCompletedTasks(folder);

			}
		});

        setGraphic(button);
    }

}
