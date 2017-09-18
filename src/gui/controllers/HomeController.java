package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.views.FolderCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.Folder;
import model.Task;
import utils.Utils;

public class HomeController implements Initializable {

	@FXML private ListView<Folder> listViewFolders;

	@FXML private AnchorPane anchorPaneTasks;

	private ObservableList<Folder> folders;
	private ObservableList<Task> tasks;

    public HomeController() {
    	folders = FXCollections.observableArrayList();
    	tasks = FXCollections.observableArrayList();
	}

	public void initialize(URL location, ResourceBundle resources) {
		initFolders();
	}

	@FXML public void addFolder(ActionEvent event) {
		Utils.createWindow(null, HomeController.this, "../fxml/Folder.fxml", "Add New Folder");
	}

	@FXML public void addTask(ActionEvent event) {
		Utils.createWindow(null, HomeController.this, "../fxml/Task.fxml", "Add New Task");
	}

	private void initFolders() {
		folders.addAll(
				new Folder("Tareas"), new Folder("Peliculas por ver"), new Folder("Mis imagenes"),
				new Folder("Tareas"), new Folder("Peliculas por ver"), new Folder("Mis imagenes"),
				new Folder("Tareas"), new Folder("Peliculas por ver"), new Folder("Mis imagenes"),
				new Folder("Tareas"), new Folder("Peliculas por ver"), new Folder("Mis imagenes"),
				new Folder("Tareas"), new Folder("Peliculas por ver"), new Folder("Mis imagenes")
		);
		listViewFolders.setItems(folders);
		listViewFolders.setSelectionModel(null);
		listViewFolders.setCellFactory(new Callback<ListView<Folder>,
	            ListCell<Folder>>() {
	                public ListCell<Folder> call(ListView<Folder> list) {
	                    return new FolderCell();
	                }
	            }
	        );
	}

}
