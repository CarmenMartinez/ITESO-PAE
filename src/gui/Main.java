package gui;


import javafx.application.Application;
import javafx.stage.Stage;
import utils.Utils;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		Utils.createWindow(primaryStage, Main.this, "./fxml/Home.fxml", "TasksFX", null, "./css/home.css");
	}

	public static void main(String[] args) {
		launch(args);
	}

}
