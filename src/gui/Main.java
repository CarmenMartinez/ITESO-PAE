package gui;


import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Stage;
import utils.Utils;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		//primaryStage.setMaximized(true);
		ResourceBundle bundle = ResourceBundle.getBundle("resources.i18n.home");
		Utils.createWindow(primaryStage, Main.this, "./fxml/Home.fxml", "TasksFX", null, "./css/home.css", bundle);


		/*primaryStage.showingProperty().addListener(new ChangeListener<Boolean>() {

			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {

				}
			}

		});*/
	}

	public static void main(String[] args) {
		launch(args);
	}

}
