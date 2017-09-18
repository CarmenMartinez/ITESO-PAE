package utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Utils {

	public static void createWindow(Stage stage, Object parent, String fxmlLocation, String sceneTitle, String cssLocation, Double sceneWidth, Double sceneHeight) {
        try {
        	Pane root = (Pane) FXMLLoader.load(parent.getClass().getResource(fxmlLocation));
            Stage targetStage = (stage != null) ? stage : new Stage();
            targetStage.setTitle(sceneTitle);
            Scene scene = (sceneWidth == null || sceneHeight == null)
            		        ? new Scene(root)
            		        : new Scene(root, sceneWidth.doubleValue(), sceneHeight.doubleValue());

            if (cssLocation != null) {
            	scene.getStylesheets().add(parent.getClass().getResource(cssLocation).toExternalForm());
            }

            targetStage.setScene(scene);
            targetStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static void createWindow(Stage stage, Object parent, String fxmlLocation, String sceneTitle) {
		createWindow(stage, parent, fxmlLocation, sceneTitle, null, null, null);
	}

	public static void createWindow(Stage stage, Object parent, String fxmlLocation, String sceneTitle, String cssLocation) {
		createWindow(stage, parent, fxmlLocation, sceneTitle, cssLocation, null, null);
	}

	public static void closeWindow(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

}
