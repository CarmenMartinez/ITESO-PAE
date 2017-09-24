package gui;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import utils.Utils;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		//primaryStage.setMaximized(true);
		Utils.createWindow(primaryStage, Main.this, "./fxml/Home.fxml", "TasksFX", null, "./css/home.css");


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
