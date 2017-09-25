package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.views.FolderCell;
import interfaces.FolderHandler;
import interfaces.TasksHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import model.Folder;
import model.Task;
import utils.Utils;

public class HomeController implements Initializable {

	@FXML private ListView<Folder> listViewFolders;

	@FXML private AnchorPane anchorPaneTasks;

	private FolderHandler folderHandler;
	private TasksHandler tasksHandler;

	private ObservableList<Folder> folders;
	private ObservableList<Task> tasks;

    public HomeController() {
    	folders = FXCollections.observableArrayList();
    	tasks = FXCollections.observableArrayList();

    	folderHandler = new FolderHandler() {

			public void addFolder(Folder folder) {
				folders.add(folder);
			}
		};

		tasksHandler = new TasksHandler() {

			public void addTask(Task task) {
				tasks.add(task);
			}

		};
	}

	public void initialize(URL location, ResourceBundle resources) {
		initFolders();
		final Delta dragDelta = new Delta();
		/*rectangle1.setOnMousePressed(new EventHandler<MouseEvent>() {
			  public void handle(MouseEvent mouseEvent) {
			    dragDelta.x = rectangle1.getLayoutX() - mouseEvent.getSceneX();
			    dragDelta.y = rectangle1.getLayoutY() - mouseEvent.getSceneY();
			    rectangle1.setId("selected-task");
			    rectangle1.toFront();
			  }
			});
		rectangle1.setOnMouseReleased(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				rectangle1.setId(null);
			}
		});
		rectangle1.setOnMouseDragged(new EventHandler<MouseEvent>() {
			  public void handle(MouseEvent mouseEvent) {
				  Bounds bounds = anchorPaneTasks.getLayoutBounds();
				  double targetX = mouseEvent.getSceneX() + dragDelta.x;
				  double targetY = mouseEvent.getSceneY() + dragDelta.y;
				  if (bounds.contains(targetX, targetY)) {
					  rectangle1.setLayoutX(targetX);
				  	rectangle1.setLayoutY(targetY);
				  }
			  }
			});*/
	}
	class Delta { double x, y; }

	@FXML public void addFolder(ActionEvent event) {
		Utils.createWindow(null, HomeController.this, "../fxml/Folder.fxml", "Add New Folder", folderHandler, "../css/folder.css");
	}

	@FXML public void addTask(ActionEvent event) {
		Utils.createWindow(null, HomeController.this, "../fxml/Task.fxml", "Add New Task", tasksHandler, "../css/task.css");
	}

	private void initFolders() {
		folders.addAll(
				new Folder("Tareas"),
				new Folder("Peliculas por ver"),
				new Folder("Mis imagenes")
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
