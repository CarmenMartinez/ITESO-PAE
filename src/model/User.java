package model;

import javafx.collections.ObservableList;

public class User {

	private int id;
	private String name, lastName, userName, email;
	private ObservableList<Folder> folders;

	public User(int id, String name, String lastName, String userName, String email) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.userName = userName;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ObservableList<Folder> getFolders() {
		return folders;
	}

	public void setFolders(ObservableList<Folder> folders) {
		this.folders = folders;
	}

}
