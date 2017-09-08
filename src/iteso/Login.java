package iteso;
<<<<<<< HEAD

=======
>>>>>>> master
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		//Se crea el VBox que sera el contenedor del login
		VBox login = new VBox();
		//Asigno propiedades
		login.setSpacing(10);
		login.setPadding(new Insets(10, 20, 10, 20));

		//Creo componentes que formaran parte del login
		Label logo = new Label("LOGO");
		TextField name = new TextField();
		TextField password = new TextField();
		Button loginBtn = new Button("Login");

		//Pongo hints a los text fields
		name.setPromptText("Nombre");
		password.setPromptText("Contraseña");

		//Los hago "Responsive"
		logo.setContentDisplay(ContentDisplay.CENTER);
		name.setMaxWidth(Double.MAX_VALUE);
		password.setMaxWidth(Double.MAX_VALUE);
		loginBtn.setMaxWidth(Double.MAX_VALUE);

		//Los agrego
		login.getChildren().addAll(logo, name, password, loginBtn);

		Scene scene = new Scene(login);
		//Asignando el estilo desde un archivo externo
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.show();

	}

	/*public static void main(String[] args) {
	launch(args);
}*/

}

