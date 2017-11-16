package main.java.gui.controllers;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.management.Notification;

import org.controlsfx.control.Notifications;

import com.sun.prism.paint.Color;

import main.java.gui.views.FolderCell;
import main.java.interfaces.FolderHandler;
import main.java.interfaces.RunnableTask;
import main.java.interfaces.TasksHandler;
import main.java.interfaces.WindowState;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import jfxtras.labs.scene.control.window.CloseIcon;
import jfxtras.labs.scene.control.window.Window;
import main.java.model.Folder;
import main.java.model.FolderManager;
import main.java.model.Task;
import main.java.model.TaskManager;
import main.java.model.User;
import main.java.utils.ThreadHandler;
import main.java.utils.ThreadHandler.ThreadHandlerException;
import main.java.utils.Utils;

public class HomeController implements WindowState, FolderHandler, TasksHandler {

	@FXML private ListView<Folder> listViewFolders;

	@FXML private AnchorPane anchorPaneTasks, anchorPaneCompletedTasks;

	private FolderManager folderManager;
	private TaskManager taskManager;

	private User user;
	private ObservableList<Folder> folders;

	private Folder currentFolder;

	private Tooltip tp;

	private Integer lastid;

    public HomeController() {
		taskManager = new TaskManager().setTaskHandler(this);
	}

    public ObservableList<Folder> getFolders() {
    		return folders;
    }

    @Override
	public void onReady() {
	    	user = (User) anchorPaneTasks.getScene().getWindow().getUserData();
	    	folderManager =  new FolderManager().setFolderHandler(this).setUser(user);
	    	folders = user.getFolders();
	    	initFolders();
	    	if (folders.size() > 0) {
	    		onFolderSelected(folders.get(0));
	    		onFolderSelectedCompletedTasks(folders.get(0));
	    	}
	    	
	}

	@FXML public void addFolder(ActionEvent event) {
		Utils.createWindow(null, HomeController.this, "fxml/Folder.fxml", "Add New Folder", folderManager, "css/folder.css", "i18n/folder");
	}

	@FXML public void addTask(ActionEvent event) {
		taskManager.setTask(null);
		openTaskWindow();
	}

	@FXML public void saveTasks(ActionEvent event) {
		for (Task task : currentFolder.getTasks()) {
			try {
				ThreadHandler.getInstance().setRunnableTask(new RunnableTask() {

					@Override
					public void onFinish(Object response) {
						if (response == null) {
							ResourceBundle bundle = ResourceBundle.getBundle("i18n/home");
							Utils.showError(bundle.getString("task_failed"));
						}
					}

				}).performUpdateTaskAttributes(task);
			} catch (InterruptedException | ExecutionException | ThreadHandlerException e) {
				e.printStackTrace();
			}
		}
	}

	private void openTaskWindow() {
		Utils.createWindow(null, HomeController.this, "fxml/Task.fxml", "Add New Task", taskManager, "css/task.css", "i18n/task");
	}

	private void initFolders() {
		listViewFolders.setItems(folders);
		listViewFolders.setCellFactory(new Callback<ListView<Folder>,
	            ListCell<Folder>>() {
	                public ListCell<Folder> call(ListView<Folder> list) {
	                    return new FolderCell(HomeController.this);
	                }
	            }
	        );
	}

	private Window createTaskUI(Task task) {
		final Window window = new Window(task.getTitle());
		window.setStyle(task.getColor());
		window.setPrefSize(task.getWidth(), task.getHeight());
		window.setLayoutX(task.getXPosition());
		window.setLayoutY(task.getYPosition());
		CloseIcon closeIcon = new CloseIcon(window);
		closeIcon.addEventHandler(
			MouseEvent.MOUSE_PRESSED,
			(MouseEvent mouseEvent) -> {
				closeTask(task);
			}
		);
		window.getRightIcons().add(closeIcon);

		final ImageView editIcon = new ImageView(new Image("img/edit.png"));
		editIcon.setFitHeight(22);
		editIcon.setFitWidth(22);

		final ImageView infoIcon = new ImageView(new Image("img/info.png"));
		infoIcon.setFitHeight(22);
		infoIcon.setFitWidth(22);

		Label labelDescription = new Label(task.getDescription());
		Button buttonEdit = new Button("",editIcon);
		Button buttonInfo = new Button("",infoIcon);

		ResourceBundle bundle = ResourceBundle.getBundle("i18n/task");
		CheckBox status = new CheckBox(bundle.getString("task_completed"));


		buttonEdit.setOnAction((event) -> {
			window.toFront();
			taskManager.setTask(task);
			openTaskWindow();
		});
		if(task.getStatus().equals(bundle.getString("task_pending"))) {
			status.setSelected(false);
		}
		else{
			status.setSelected(true);
		}
		
		
		tp = new Tooltip();
		if (task.isReminder()) {
			tp.setText(task.getStatus() + "\n" + "Fecha: " + task.getReminderDate().toString());
		} else {
			tp.setText(task.getStatus());
		}
		
		status.setOnAction((event) -> {
			onStatusChanged(task, status.isSelected());
		});


		status.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, Boolean old_val, Boolean new_val) {
            	 	if(new_val) {
            	 		task.setStatus(bundle.getString("task_completed"));
            	 	}
            	 	else{
            	 		task.setStatus(bundle.getString("task_pending"));
            	 	}
        			tp.setText(task.getStatus());
            }
        });

		if(task.getStatus().equals(bundle.getString("task_pending"))) {
			status.setSelected(false);
		}
		else{
			status.setSelected(true);
		}


		buttonEdit.getStyleClass().add("buttons-task");
		buttonInfo.getStyleClass().add("buttons-task");
		buttonInfo.setTooltip(tp);
		ScrollPane scrollPane = new ScrollPane(labelDescription);
		scrollPane.getStyleClass().add("task-description");

		scrollPane.setStyle("-fx-background: " + task.getOnlyColor());

		HBox hbox = new HBox(status,buttonEdit,buttonInfo);

		hbox.setAlignment(Pos.BOTTOM_RIGHT);
		VBox vBox = new VBox(scrollPane, hbox);
		vBox.setAlignment(Pos.TOP_CENTER);
		vBox.setSpacing(10);
		window.setContentPane(vBox);
		window.setBoundsListenerEnabled(false);
		window.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            public void changed(ObservableValue<? extends Bounds> ov, Bounds t, Bounds t1) {
                if (window.getParent() != null) {
                    if (t1.equals(t)) {
                        return;
                    }
                    task.setWidth(window.getWidth());
                    task.setHeight(window.getHeight());
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
		window.setStyle(task.getColor());
		Label label = (Label) ((ScrollPane) window.getContentPane().getChildren().get(0)).getContent();
		label.setText(task.getDescription());
		ScrollPane scroll = (ScrollPane) window.getContentPane().getChildren().get(0);
		scroll.setStyle("-fx-background:" + task.getOnlyColor());
	}
	
	private void refreshAnchorPane(Folder folder) {
		anchorPaneTasks.getChildren().clear();
		ResourceBundle bundle = ResourceBundle.getBundle("i18n/task");


		ObservableList<Task> tasks = folder.getTasks();
		if (tasks == null) return;
		tasks.forEach(task -> {
			if(task.getStatus().equals(bundle.getString("task_pending")))
				anchorPaneTasks.getChildren().add(createTaskUI(task));
		});
	}
	
	private void refreshAnchorPaneCompletedTasks(Folder folder) {
		anchorPaneCompletedTasks.getChildren().clear();
		ResourceBundle bundle = ResourceBundle.getBundle("i18n/task");

		ObservableList<Task> tasks = folder.getTasks();
		if (tasks == null) return;
		tasks.forEach(task -> {
		if(task.getStatus().equals(bundle.getString("task_completed")))
			anchorPaneCompletedTasks.getChildren().add(createTaskUI(task));
		});
	}

	@Override
	public void addFolder(Folder folder) {
		boolean isFavorite = folder.isFavorite();
		Folder actualFolder;
		int i;
		for (i = 0; i < folders.size(); i++) {
			actualFolder = folders.get(i);
			if (isFavorite) {
				if (!actualFolder.isFavorite() ||
						folder.getName().compareTo(actualFolder.getName()) < 0)
					break;
			} else {
				if (actualFolder.isFavorite()) continue;
				if (folder.getName().compareTo(actualFolder.getName()) < 0) break;
			}
		}
		if (i < folders.size()) {
			folders.add(i, folder);
		} else {
			folders.add(folder);
		}
		folder.setTasksLoaded(true);
		onFolderSelected(folder);
	}

	@Override
	public void onFolderSelected(Folder folder) {
		currentFolder = folder;
		taskManager.setFolderId(folder.getId());
		//currentFolder.button.setStyle("-fx-background-color: #999999");

		if (!folder.hasTasksLoaded()) {
			try {
				ThreadHandler.getInstance().setRunnableTask(new RunnableTask() {

					@SuppressWarnings("unchecked")
					@Override
					public void onFinish(Object response) {
						if (response == null) {
							ResourceBundle bundle = ResourceBundle.getBundle("i18n/home");
							Utils.showError(bundle.getString("error_get_task"));
							return;
						}
						folder.setTasks((ObservableList<Task>) response);
						refreshAnchorPane(folder);
						
					}

				}).performGetFolderTasks(folder.getId());
			} catch (InterruptedException | ExecutionException | ThreadHandlerException e) {
				e.printStackTrace();
			}
		} else {
			refreshAnchorPane(folder);
		}
	}

	@Override
	public void onFolderDeleted(Folder folder) {
		try {
			ThreadHandler.getInstance().setRunnableTask(new RunnableTask() {

				@Override
				public void onFinish(Object response) {
					if (response == null) {
						ResourceBundle bundle = ResourceBundle.getBundle("i18n/home");
						Utils.showError(bundle.getString("error_folder"));
					}
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
			}).performDeleteFolder(folder);
		} catch (InterruptedException | ExecutionException | ThreadHandlerException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void addTask(Task task) {
		currentFolder.getTasks().add(task);
		anchorPaneTasks.getChildren().add(createTaskUI(task));
	}

	@Override
	public void updateTask(Task task) {
		List<Task> tasks = currentFolder.getTasks();
		tasks.set(tasks.indexOf(task), task);
		updateTaskUI(task);
	}

	@Override
	public void onFolderRemoved(Folder folder) {
		folders.remove(folder);
		folder.setFavorite(false);
		addFolder(folder);
	}

	@Override
	public void onFolderAdded(Folder folder) {
		folders.remove(folder);
		folder.setFavorite(true);
		addFolder(folder);
	}

	@Override
	public void onStatusChanged(Task task,boolean status) {
		ResourceBundle bundle = ResourceBundle.getBundle("i18n/task");
		if(status) {
 			task.setStatus(bundle.getString("task_completed"));
		}
		else{
	 			task.setStatus(bundle.getString("task_pending"));
	 	}	
		refreshAnchorPane(currentFolder); 	
		refreshAnchorPaneCompletedTasks(currentFolder);
	}

	@Override
	public void closeTask(Task task) {
		try {
			ThreadHandler.getInstance().setRunnableTask(new RunnableTask() {
				@Override
				public void onFinish(Object response) {
					if (response == null) {
						ResourceBundle bundle = ResourceBundle.getBundle("i18n/home");
						Utils.showError(bundle.getString("delete_task"));
						return;
					}
					currentFolder.getTasks().remove(task);
				}

			}).performDeleteTask(task);
		} catch (InterruptedException | ExecutionException | ThreadHandlerException e) {
			e.printStackTrace();
		}		
	}
	

	@Override
	public void onFolderSelectedCompletedTasks(Folder folder) {
		currentFolder = folder;
		taskManager.setFolderId(folder.getId());

		if (!folder.hasTasksLoaded()) {
			try {
				ThreadHandler.getInstance().setRunnableTask(new RunnableTask() {

					@SuppressWarnings("unchecked")
					@Override
					public void onFinish(Object response) {
						if (response == null) {
							ResourceBundle bundle = ResourceBundle.getBundle("i18n/home");
							Utils.showError(bundle.getString("error_get_task"));
							return;
						}
						folder.setTasks((ObservableList<Task>) response);
						refreshAnchorPaneCompletedTasks(folder);
					}

				}).performGetFolderTasks(folder.getId());
			} catch (InterruptedException | ExecutionException | ThreadHandlerException e) {
				e.printStackTrace();
			}
		} else {
			refreshAnchorPaneCompletedTasks(folder);
		}
	}
	 

}
