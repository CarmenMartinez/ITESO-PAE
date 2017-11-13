package main.java.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.utils.ThreadHandler;
import main.java.utils.Utils;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		Utils.createWindow(primaryStage, Main.this, "fxml/Login.fxml", "Login", null, "css/login.css", "i18n/login");
	}

	@Override
	public void stop() throws Exception {
		ThreadHandler.getInstance().shutdown();
		super.stop();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
