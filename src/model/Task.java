package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {

	private int id;

	private StringProperty title;
	private StringProperty description;
	private String color;

	private NumberAttributes delta, size;

	public Task(int id, String title, String description, double xPosition, double yPosition, double width, double height, String color) {
		this.id = id;
		this.title = new SimpleStringProperty(title);
		this.description = new SimpleStringProperty(description);
		this.delta = new NumberAttributes(xPosition, yPosition);
		this.size = new NumberAttributes(width,  height);
		this.color = color;
	}

	public Task(String title, String description, double xPosition, double yPosition, double width, double height, String color) {
		this((int) Math.random(), title, description, xPosition, yPosition, width, height, color);
	}

	public Task(String title, String description) {
		this(title, description, 10, 10, 200, 120, "#f4f4f4");
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title.getValue();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public String getDescription() {
		return description.getValue();
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public void setXPosition(double xPosition) {
		this.delta.x = xPosition;
	}

	public double getXPosition() {
		return this.delta.x;
	}

	public void setYPosition(double yPosition) {
		this.delta.y = yPosition;
	}

	public double getYPosition() {
		return this.delta.y;
	}

	public void setWidth(double width) {
		this.size.x = width;
	}

	public double getWidth() {
		return this.size.x;
	}

	public void setHeight(double height) {
		this.size.y = height;
	}

	public double getHeight() {
		return this.size.y;
	}

	public String getColor() {
		return "-fx-background-color: " + color + ";";
	}

	public void setColor(String color) {
		this.color = color;
	}

	private class NumberAttributes {
		double x, y;

		public NumberAttributes(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Task)) return false;
		return ((Task) obj).getId() == this.id;
	}

}
