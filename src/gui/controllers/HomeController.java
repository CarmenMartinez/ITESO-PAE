package gui.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.views.FolderCell;
import interfaces.FolderHandler;
import interfaces.TasksHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import jfxtras.labs.scene.control.window.CloseIcon;
import jfxtras.labs.scene.control.window.Window;
import model.Folder;
import model.Task;
import model.TaskManager;
import utils.Utils;

public class HomeController implements Initializable {

	@FXML private ListView<Folder> listViewFolders;

	@FXML private AnchorPane anchorPaneTasks;

	private FolderHandler folderHandler;
	private TasksHandler tasksHandler;
	private TaskManager taskManager;

	private ObservableList<Folder> folders;

	private Folder currentFolder;

	private ListChangeListener<Task> tasksChangeListener;

    public HomeController() {
    	folders = FXCollections.observableArrayList();

    	tasksChangeListener = new ListChangeListener<Task>() {

			public void onChanged(Change<? extends Task> change) {
				while (change.next()) {
					if (change.wasReplaced()) {
						Task task = change.getAddedSubList().get(0);
						updateTaskUI(task);
					} else if (change.wasAdded()) {
						Task task = change.getAddedSubList().get(0);
						currentFolder.getTasks().add(task);
						anchorPaneTasks.getChildren().add(createTaskUI(task));
					} else if (change.wasRemoved()) {
						System.out.println("REMO");
					}
				}
			}

		};

    	folderHandler = new FolderHandler() {

			public void addFolder(Folder folder) {
				folders.add(folder);
				onFolderSelected(folder);
			}

			@Override
			public void onFolderSelected(Folder folder) {
				currentFolder = folder;
				initTasks(currentFolder);
				anchorPaneTasks.getChildren().clear();
				ObservableList<Task> tasks = folder.getTasks();
				if (tasks == null) return;
				tasks.forEach(task -> {
					anchorPaneTasks.getChildren().add(createTaskUI(task));
				});
			}

			@Override
			public void onFolderDeleted(Folder folder) {
				int folderIndex = folders.indexOf(folder);
				int foldersSize = folders.size();
				folders.remove(folder);
				if (foldersSize == 1) {
					anchorPaneTasks.getChildren().clear();
					return;
				}
				if (folder == currentFolder) {
					onFolderSelected(folders.get(foldersSize - 1 == folderIndex ? folderIndex - 1 : folderIndex));
				}
			}

		};

		tasksHandler = new TasksHandler() {

			public void addTask(Task task) {
				currentFolder.getTasks().add(task);
			}

			@Override
			public void updateTask(Task task) {
				List<Task> tasks = currentFolder.getTasks();
				tasks.set(tasks.indexOf(task), task);
			}

		};
		taskManager = new TaskManager().setTaskHandler(tasksHandler);
	}

	public void initialize(URL location, ResourceBundle resources) {
		initFolders();
		folderHandler.onFolderSelected(folders.get(0));
	}

	@FXML public void addFolder(ActionEvent event) {
		Utils.createWindow(null, HomeController.this, "../fxml/Folder.fxml", "Add New Folder", folderHandler, "../css/folder.css");
	}

	@FXML public void addTask(ActionEvent event) {
		taskManager.setTask(null);
		openTaskWindow();
	}

	private void openTaskWindow() {
		Utils.createWindow(null, HomeController.this, "../fxml/Task.fxml", "Add New Task", taskManager, "../css/task.css");
	}

	private void initTasks(Folder folder) {
		if (folder == null || folder.getTasks() == null) { return; }
		folder.getTasks().removeListener(tasksChangeListener);
		folder.getTasks().addListener(tasksChangeListener);
	}

	private void initFolders() {
		ObservableList<Task> tasks1 = FXCollections.observableArrayList();
		tasks1.add(new Task("Tarea1", "Hacerla hoy"));
		tasks1.add(new Task("Tarea 2", "Ayer"));
		ObservableList<Task> tasks2 = FXCollections.observableArrayList();
		tasks2.add(new Task("Presagio", "Hoy"));
		folders.addAll(
				new Folder("Tareas", tasks1),
				new Folder("Peliculas por ver", tasks2),
				new Folder("Mis imagenes")
		);
		listViewFolders.setItems(folders);
		listViewFolders.setSelectionModel(null);
		listViewFolders.setCellFactory(new Callback<ListView<Folder>,
	            ListCell<Folder>>() {
	                public ListCell<Folder> call(ListView<Folder> list) {
	                    return new FolderCell(folderHandler);
	                }
	            }
	        );
	}

	private Window createTaskUI(Task task) {
		final Window window = new Window(task.getTitle());
		window.setPrefSize(task.getWidth(), task.getHeight());
		window.setLayoutX(task.getXPosition());
		window.setLayoutY(task.getYPosition());
		CloseIcon closeIcon = new CloseIcon(window);
		closeIcon.addEventHandler(
			MouseEvent.MOUSE_PRESSED,
			(MouseEvent mouseEvent) -> { System.out.println("Remove Task!"); }
		);
		window.getRightIcons().add(closeIcon);

		final ImageView editIcon = new ImageView(
	      	      new Image("/img/edit.png")
	      	    );
		editIcon.setFitHeight(22);
		editIcon.setFitWidth(22);

		final ImageView infoIcon = new ImageView(
	      	      new Image("/img/info.png")
	      	    );
		infoIcon.setFitHeight(22);
		infoIcon.setFitWidth(22);

		Label labelDescription = new Label(task.getDescription());
		Button buttonEdit = new Button("",editIcon);
		Button buttonInfo = new Button("",infoIcon);

		buttonEdit.setOnAction((event) -> {
			window.toFront();
			taskManager.setTask(task);
			openTaskWindow();
		});

		buttonEdit.getStyleClass().add("buttons-task");
		buttonInfo.getStyleClass().add("buttons-task");
		ScrollPane scrollPane = new ScrollPane(labelDescription);
		scrollPane.getStyleClass().add("task-description");
		HBox hbox = new HBox(buttonEdit,buttonInfo);
		hbox.setAlignment(Pos.BOTTOM_RIGHT);
		VBox vBox = new VBox(scrollPane, hbox);
		vBox.setAlignment(Pos.TOP_CENTER);
		vBox.setSpacing(10);
		vBox.setStyle(task.getColor());
		window.setContentPane(vBox);

		window.setBoundsListenerEnabled(false);
		window.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            public void changed(ObservableValue<? extends Bounds> ov, Bounds t, Bounds t1) {
                if (window.getParent() != null) {
                    if (t1.equals(t)) {
                        return;
                    }

                    window.getParent().requestLayout();

                    double x = Math.max(0, window.getLayoutX());
                    double y = Math.max(0, window.getLayoutY());

                    Bounds bounds = anchorPaneTasks.getLayoutBounds();
                    if (bounds.contains(x, y)) {
                    	window.setLayoutX(x);
                    	window.setLayoutY(y);
                    	task.setXPosition(x);
                    	task.setYPosition(y);
                    }
                }
            }
        });
		return window;
	}

	private void updateTaskUI(Task task) {
		Window window = (Window) anchorPaneTasks.getChildren().get(anchorPaneTasks.getChildren().size() - 1);
		window.setTitle(task.getTitle());
		Label label = (Label) ((ScrollPane) window.getContentPane().getChildren().get(0)).getContent();
		label.setText(task.getDescription());
	}

}
