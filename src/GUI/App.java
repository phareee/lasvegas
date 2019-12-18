package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {
	
	public static Stage stage;

	@Override
	public void start(Stage primaryStage) {
		
		this.stage = primaryStage;
		
		AnchorPane menu = new Menu();
		
		Scene scene = new Scene(menu);
		primaryStage.setTitle("LasVegas");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main (String [] args) {
		launch(args);
	}

}
