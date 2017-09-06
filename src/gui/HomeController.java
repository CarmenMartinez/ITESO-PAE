package gui;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.Folder;
import model.Task;

public class HomeController implements Initializable {

	@FXML private Button buttonAddFolder, buttonAddTask;

	@FXML private ListView<Folder> listViewFolders;

	@FXML private AnchorPane anchorPaneTasks;

	private ObservableList<Folder> folders;
	private ObservableList<Task> tasks;

    public HomeController() {
    	folders = FXCollections.observableArrayList();
    	tasks = FXCollections.observableArrayList();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initFolders();

	}

	@FXML public void addFolder(ActionEvent event) {

	}

	@FXML public void addTask(ActionEvent event) {

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
	                @Override
	                public ListCell<Folder> call(ListView<Folder> list) {
	                    return new FolderCell();
	                }
	            }
	        );
	}

}