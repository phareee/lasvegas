package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class Menu extends AnchorPane {
	
	private Button playbutton;
	private Button how2playbutton;
	private Button exitbutton;
	
	public Menu() {
		
		setPrefHeight(666);
		setPrefWidth(1000);
		
		String image = ClassLoader.getSystemResource("menubackground.png").toString();
		ImageView bg = new ImageView(image);
		bg.setFitWidth(1000);
		bg.setPreserveRatio(true);
		
		playbutton = new Button();
		setPlayButton();
		
		how2playbutton = new Button();
		setHow2PlayButton();
		
		exitbutton = new Button();
		setExitButton();
		
		HBox hbox = new HBox();
		hbox.setPrefWidth(1000);
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(30);
		
		hbox.getChildren().addAll(playbutton,how2playbutton,exitbutton);
		
		getChildren().addAll(bg,hbox);
		setBottomAnchor(hbox, 60.0);
		
	}
	
	private void setImage(Button button,String url) {
		
		String image = ClassLoader.getSystemResource(url).toString();
		ImageView img = new ImageView(image);
		img.setFitWidth(200);
		img.setPreserveRatio(true);
		button.setGraphic(img);
		
	}
	
	private void setPlayButton() {
		setImage(playbutton, "play1.png");
		playbutton.setCursor(Cursor.HAND);
		playbutton.setStyle("-fx-background-color: transparent;");
		playbutton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setImage(playbutton,"play2.png");
			}
		});
		playbutton.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setImage(playbutton,"play1.png");
			}
		});
		playbutton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					GameSetting gamesetting = new GameSetting();
					Scene scene = new Scene(gamesetting);
		            App.stage.setScene(scene);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
			
		});
	}
	
	private void setHow2PlayButton() {
		setImage(how2playbutton, "howtoplay1.png");
		how2playbutton.setCursor(Cursor.HAND);
		how2playbutton.setStyle("-fx-background-color: transparent;");
		how2playbutton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setImage(how2playbutton,"howtoplay2.png");
			}
		});
		how2playbutton.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setImage(how2playbutton,"howtoplay1.png");
			}
		});
		how2playbutton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					HowtoPlay how2play = new HowtoPlay();
					Scene scene = new Scene(how2play);
		            App.stage.setScene(scene);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
			
		});
	}
	
	private void setExitButton() {
		setImage(exitbutton, "exit1.png");
		exitbutton.setCursor(Cursor.HAND);
		exitbutton.setStyle("-fx-background-color: transparent;");
		exitbutton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setImage(exitbutton,"exit2.png");
			}
		});
		exitbutton.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setImage(exitbutton,"exit1.png");
			}
		});
		exitbutton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					App.stage.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
			
		});
		
	}
}
