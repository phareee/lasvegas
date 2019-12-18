package GUI;

import java.util.HashMap;
import java.util.Map;
import Casino.ComputerPlayer;
import Casino.Player;
import Casino.Table;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.geometry.Pos;
import javafx.scene.Scene;

public class GameSetting extends AnchorPane{
    private static Map<String, Double> BotAggression = new HashMap<>();

    static {
        BotAggression.put("Bear", 0.8);
        BotAggression.put("Cat", 0.2);
        BotAggression.put("Dog", 0.2);
        BotAggression.put("Eagle", 0.6);
        BotAggression.put("Elephant", 0.5);
        BotAggression.put("Horse", 0.6);
        BotAggression.put("Lion", 0.8);
        BotAggression.put("Monkey", 0.3);
        BotAggression.put("Panda", 0.3);
        BotAggression.put("Penguin", 0.1);
        BotAggression.put("Rabbit", 0.0);
        BotAggression.put("Seal", 0.0);
        BotAggression.put("Tiger", 0.8);
        BotAggression.put("Wolf", 0.7);
        BotAggression.put("Zebra", 0.7);
    }

    private HBox botsBox;
    private Slider numberOfBotSlider;
    private Button startGameButton;
    private Button back2menuButton;
    private TextField smallBlindText;
    private TextField bigBlindText;
    private TextField startingChipsText;

    public GameSetting() {
    	
    	String image = ClassLoader.getSystemResource("background.png").toString();
		ImageView bg = new ImageView(image);
		bg.setFitWidth(1000);
		bg.setPreserveRatio(true);
    	
    	Label label = new Label("Select Number of Bot Players :");
    	label.setStyle("-fx-font-size: 14pt; -fx-text-fill: white;");
    	
    	numberOfBotSlider = new Slider();
    	addSlider();
    	
    	GridPane grid = new GridPane();
    	Label smallblind = new Label("Small Blind :");
    	smallblind.setStyle("-fx-font-size: 12pt; -fx-text-fill: white;");
    	Label bigblind = new Label("Big Blind :");
    	bigblind.setStyle("-fx-font-size: 12pt; -fx-text-fill: white;");
    	Label startingchips = new Label("Starting Chips : ");
    	startingchips.setStyle("-fx-font-size: 12pt; -fx-text-fill: white;");
    	smallBlindText = new TextField("10");
    	bigBlindText = new TextField("20");
    	startingChipsText = new TextField("1500");
    	grid.add(smallblind, 0, 0);
    	grid.add(bigblind, 0, 1);
    	grid.add(startingchips, 0, 2);
    	grid.add(smallBlindText, 1, 0);
    	grid.add(bigBlindText, 1, 1);
    	grid.add(startingChipsText, 1, 2);
    	
    	HBox hbox = new HBox();
    	hbox.setPrefWidth(1000);
    	hbox.setSpacing(30);
    	hbox.setAlignment(Pos.CENTER);
    	hbox.getChildren().addAll(label,numberOfBotSlider,grid);
    	
    	botsBox = new HBox();
    	botsBox.getChildren().add(new BotPane());
    	
    	HBox button = new HBox();
    	button.setSpacing(30);
    	button.setPrefWidth(1000);
    	button.setAlignment(Pos.CENTER);
    	
    	startGameButton = new Button();
    	setStartGameButton();
    	
		back2menuButton = new Button();
		setBack2MenuButton();
    	
    	button.getChildren().addAll(startGameButton,back2menuButton);
    	getChildren().addAll(bg,hbox,botsBox,button);
    	
    	setTopAnchor(hbox, 60.0);
    	setTopAnchor(botsBox, 200.0);
    	setLeftAnchor(botsBox, 7.5);
    	setBottomAnchor(button, 60.0);
    	
    }
   
    private void addSlider() {
    	numberOfBotSlider.setMin(1);
    	numberOfBotSlider.setMax(5);
    	numberOfBotSlider.setValue(1);
    	numberOfBotSlider.setShowTickLabels(true);
    	numberOfBotSlider.setShowTickMarks(true);
    	numberOfBotSlider.setMajorTickUnit(1);
    	numberOfBotSlider.setMinorTickCount(0);
    	numberOfBotSlider.setBlockIncrement(1);
    	numberOfBotSlider.setSnapToTicks(true);
    	numberOfBotSlider.setCursor(Cursor.OPEN_HAND);
    	numberOfBotSlider.setPrefWidth(200);
        numberOfBotSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal.intValue() < newVal.intValue()) {
                for (int x = 0; x < newVal.intValue() - oldVal.intValue(); x++) {
                    botsBox.getChildren().add(new BotPane());
                }
            } else if (oldVal.intValue() > newVal.intValue()) {
                for (int x = 0; x < oldVal.intValue() - newVal.intValue(); x++) {
                    botsBox.getChildren().remove(botsBox.getChildren().size() - x - 1);
                }
            }
        });
    }
    
    private void setButtonImage(Button button,String url) {
    	String image = ClassLoader.getSystemResource(url).toString();
		ImageView img = new ImageView(image);
		img.setFitWidth(200);
		img.setPreserveRatio(true);
    	button.setGraphic(img);
    }

    private void startGameButtonClick() {
        Table table = new Table();
        table.setBlinds(Integer.parseInt(smallBlindText.getText()), Integer.parseInt(bigBlindText.getText()));
        int numberOfChips = Integer.parseInt(startingChipsText.getText());

        Player player = new Player(table);
        player.numberOfChips = numberOfChips;
        player.avatar = new Image("player.png");
        table.players.add(player);

        for (Node node : botsBox.getChildren()) {
            BotPane botPane = (BotPane) node;
            if (!botPane.botChoosen)
                return;
            Player newComputerPlayer = new ComputerPlayer(table, BotAggression.get(botPane.BotName));
            newComputerPlayer.numberOfChips = numberOfChips;
            newComputerPlayer.avatar = botPane.avatarImage;

            table.players.add(newComputerPlayer);
        }

        loadGame(table);
    }
    
    private void loadGame(Table table) {
        try {
					Game game = new Game(table);
					Scene scene = new Scene(game);
		            App.stage.setScene(scene);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
    }
    
    private void setStartGameButton() {
    	setButtonImage(startGameButton, "start1.png");
    	startGameButton.setCursor(Cursor.HAND);
    	startGameButton.setStyle("-fx-background-color: transparent;");
    	
    	startGameButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setButtonImage(startGameButton, "start2.png");
			}
		});
		
		startGameButton.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setButtonImage(startGameButton, "start1.png");
			}
		});
		
		startGameButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				startGameButtonClick();
			}
			
		});
    }
    
    private void setBack2MenuButton() {
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

}