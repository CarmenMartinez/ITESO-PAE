package main.java.model;

import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {

	public static final String DEFAULT_COLOR = "#f4f4f4";
	public static final double DEFAULT_POSITION = 10.0;
	public static final double DEFAULT_WIDTH = 200.0;
	public static final double DEFAULT_HEIGHT = 120.0;

	private int id;

	private StringProperty title;
	private StringProperty description;
	private LocalDateTime creationDate, reminderDate;
	private String color, status;

	private NumberAttributes delta, size;

	public Task(int id, String title, String description, double xPosition, double yPosition, double width, double height, String color, String status, LocalDateTime creationDate, LocalDateTime reminderDate) {
		this.id = id;
		this.title = new SimpleStringProperty(title);
		this.description = new SimpleStringProperty(description);
		this.delta = new NumberAttributes(xPosition, yPosition);
		this.size = new NumberAttributes(width,  height);
		this.color = color;
		this.status = status;
		this.creationDate = creationDate;
		this.reminderDate = reminderDate;
		ResourceBundle bundle = ResourceBundle.getBundle("i18n/task");
		this.status = bundle.getString("task_pending");
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

	public void setReminderDate(LocalDateTime reminderDate) {
		this.reminderDate = reminderDate;
	}

	public LocalDateTime getReminderDate() {
		return reminderDate;
	}

	public boolean isReminder() {
		return reminderDate != null;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Task)) return false;
		return ((Task) obj).getId() == this.id;
	}

	private class NumberAttributes {
		double x, y;

		public NumberAttributes(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", description=" + description + "]";
	}

}
