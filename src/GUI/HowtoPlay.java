package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class HowtoPlay extends AnchorPane{
	
	private Button back2menuButton;
	private ScrollPane scrollpane;
	
	public HowtoPlay() {
		VBox vbox = new VBox();
		vbox.setSpacing(15);
		vbox.setPrefWidth(900);
		vbox.setAlignment(Pos.CENTER);
		
		String image = ClassLoader.getSystemResource("menubackground.png").toString();
		ImageView bg = new ImageView(image);
		bg.setFitWidth(1000);
		bg.setPreserveRatio(true);
		
		back2menuButton = new Button();
		setBack2menuButton();
		
		scrollpane = new ScrollPane();
		setScrollPane();
		
		vbox.getChildren().addAll(scrollpane,back2menuButton);
		getChildren().addAll(bg,vbox);
		setTopAnchor(vbox, 20.0);
		setLeftAnchor(vbox, 50.0);
		
	}
	
	private void setButtonImage(Button button,String url) {
    	String image = ClassLoader.getSystemResource(url).toString();
		ImageView img = new ImageView(image);
		img.setFitHeight(60);
		img.setPreserveRatio(true);
    	button.setGraphic(img);
	}
	
	private void setBack2menuButton() {
		setButtonImage(back2menuButton, "backtomenu1.png");
		back2menuButton.setCursor(Cursor.HAND);
    	back2menuButton.setStyle("-fx-background-color: transparent;");
    	
    	back2menuButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setButtonImage(back2menuButton, "backtomenu2.png");
			}
		});
		
    	back2menuButton.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setButtonImage(back2menuButton, "backtomenu1.png");
			}
		});
		
    	back2menuButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					Menu menu = new Menu();
					Scene scene = new Scene(menu);
		            App.stage.setScene(scene);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
			
		});
		
	}
	private void setScrollPane() {
		
		scrollpane.setPrefSize(500, 550);
		
		String image = ClassLoader.getSystemResource("howtoplaypane.png").toString();
		ImageView how2play = new ImageView(image);
		how2play.setFitWidth(900);
		how2play.setPreserveRatio(true);
		
		scrollpane.setContent(how2play);
		scrollpane.fitToWidthProperty().set(true);
		
	}

}
