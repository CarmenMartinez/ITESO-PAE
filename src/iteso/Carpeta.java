package iteso;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Carpeta extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Carpeta.fxml"));
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			 EventHandler<ActionEvent> action = new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	Alert alert = new Alert(AlertType.ERROR);
                        alert.setHeaderText("Entrada inv�lida. Por favor ingresa n�meros reales.");
                        alert.show();
		            }
		     };


			primaryStage.setScene(scene);
			primaryStage.setTitle("TasksFX");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*public static void main(String[] args) {
		launch(args);
	}*/
}