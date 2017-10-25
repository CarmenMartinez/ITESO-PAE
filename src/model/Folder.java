package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Folder {

	private StringProperty name;
	private IntegerProperty id;
	private ObservableList<Task> tasks;
	private boolean isFavorite;

	private Folder(String name) {
		this.name = new SimpleStringProperty(name);
	}

	public Folder(int id, String name) {
		this(name);
		this.id = new SimpleIntegerProperty(id);
		tasks = FXCollections.observableArrayList();
	}

	public Folder(int id, String name, ObservableList<Task> tasks) {
		this(id, name);
		this.tasks = tasks;
	}

	public Folder(String name, ObservableList<Task> tasks) {
		this(name);
		this.tasks = tasks;
	}

	public Folder(int id, String name, ObservableList<Task> tasks, boolean isFavorite) {
		this(id, name, tasks);
		this.isFavorite = isFavorite;
	}

	public Folder(String name, ObservableList<Task> tasks, boolean isFavorite) {
		this(name, tasks);
		this.isFavorite = isFavorite;
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
	}

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

}
