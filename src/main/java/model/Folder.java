package main.java.model;

import java.time.LocalDateTime;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class Folder {

	private StringProperty name;
	private IntegerProperty id;
	private ObservableList<Task> tasks;
	private boolean isFavorite;
	private String status;
	private LocalDateTime creationDate;

	public Button button;

	private boolean hasTasksLoaded;

	public Folder(int id, String name, boolean isFavorite, String status, LocalDateTime creationDate) {
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.isFavorite = isFavorite;
		this.status = status;
		this.creationDate = creationDate;
		tasks = FXCollections.observableArrayList();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String getName() {
		return this.name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public int getId() {
		return this.id.get();
	}

	public ObservableList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ObservableList<Task> tasks) {
		this.tasks = tasks;
		setTasksLoaded(true);
	}

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	public boolean hasTasksLoaded() {
		return hasTasksLoaded;
	}

	public void setTasksLoaded(boolean hasTasksLoaded) {
		this.hasTasksLoaded = hasTasksLoaded;
	}

}
