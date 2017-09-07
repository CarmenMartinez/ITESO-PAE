package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Folder {

	private StringProperty name;
	private IntegerProperty id;

	public Folder(String name) {
		this.name = new SimpleStringProperty(name);
	}

	public String getName() {
		return this.name.get();
	}

	public void setName(String name) {
		this.name.set(name);;
	}

}
