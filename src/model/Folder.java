package model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Folder {

	private StringProperty name;
	private IntegerProperty id;
	private List<Task> tasks;

	public Folder(String name) {
		this.name = new SimpleStringProperty(name);
		if (tasks == null) {
			tasks = new ArrayList<Task>();
		}
	}

	public Folder(int id, String name) {
		this(name);
		this.id = new SimpleIntegerProperty(id);
	}

	public Folder(int id, String name, List<Task> tasks) {
		this(id, name);
		this.tasks = tasks;
	}

	public Folder(String name, List<Task> tasks) {
		this(name);
		this.tasks = tasks;
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

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}
