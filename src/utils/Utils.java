package utils;

import java.util.Locale;
import java.util.ResourceBundle;

import interfaces.WindowState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Utils {

	public static void createWindow(Stage stage, Object parent, String fxmlLocation, String sceneTitle, Object userData, String cssLocation, Double sceneWidth, Double sceneHeight) {
        try {
        	Locale locale = Locale.getDefault();
        	String resourceLocation = "resources.i18n.UIResources";
        	ResourceBundle bundle = ResourceBundle.getBundle(resourceLocation, locale);
        	FXMLLoader fxmlLoader = new FXMLLoader(parent.getClass().getClassLoader().getResource(fxmlLocation),bundle);
        	Pane root = (Pane) fxmlLoader.load();
            Stage targetStage = (stage != null) ? stage : new Stage();
            targetStage.setTitle(sceneTitle);
            Scene scene = (sceneWidth == null || sceneHeight == null)
            		        ? new Scene(root)
            		        : new Scene(root, sceneWidth.doubleValue(), sceneHeight.doubleValue());

            if (cssLocation != null) {
            	scene.getStylesheets().add(parent.getClass().getResource(cssLocation).toExternalForm());
            }

            if (stage == null) {
            	targetStage.initModality(Modality.APPLICATION_MODAL);
            	targetStage.setUserData(userData);
            	targetStage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {

					@Override
					public void handle(WindowEvent event) {
						if (fxmlLoader.getController() instanceof WindowState) {
							((WindowState) fxmlLoader.getController()).onReady();
						}
					}

            	});
            }
            targetStage.setScene(scene);
            targetStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static void createWindow(Stage stage, Object parent, String fxmlLocation, String sceneTitle, Object userData) {
		createWindow(stage, parent, fxmlLocation, sceneTitle, userData, null, null, null);
	}

	public static void createWindow(Stage stage, Object parent, String fxmlLocation, String sceneTitle, Object userData, String cssLocation) {
		createWindow(stage, parent, fxmlLocation, sceneTitle, userData, cssLocation, null, null);
	}

	public static void closeWindow(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

}
