package gui;


import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Stage;
import utils.Utils;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		//primaryStage.setMaximized(true);
		Utils.createWindow(primaryStage, Main.this, "./fxml/Home.fxml", "TasksFX", null, "./css/Home.css", "resources.i18n.Home");

	}

	public static void main(String[] args) {
		launch(args);
	}

}
