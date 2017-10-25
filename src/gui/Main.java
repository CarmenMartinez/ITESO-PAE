package gui;


import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Stage;
import utils.Utils;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		//primaryStage.setMaximized(true);

		Utils.createWindow(primaryStage, Main.this, "./fxml/Login.fxml", "TasksFX", null, "./css/login.css", "resources.i18n.login");

	}

	public static void main(String[] args) {
		launch(args);
	}

}
