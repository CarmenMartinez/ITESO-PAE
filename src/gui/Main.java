package gui;

import javafx.application.Application;
import javafx.stage.Stage;
import utils.Utils;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		Utils.createWindow(primaryStage, Main.this, "./fxml/Login.fxml", "Login", null, "./css/login.css", "resources.i18n.login");
	}

	public static void main(String[] args) {
		launch(args);
	}

}
