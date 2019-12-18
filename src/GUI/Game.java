package GUI;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import Casino.*;
import javafx.beans.value.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Actions.*;
import Exceptions.ActionException;
import Utilities.Command;
import Utilities.TriConsumer;
import javafx.scene.input.*;
import javafx.application.Platform;

public class Game extends AnchorPane {

    private static final double TABLE_CARD_HEIGHT = 70.0;
    private static final int BOT_WAIT_MILISECONDS = 1000;
    private static final double FOLD_OPACITY = 0.3;
    private static final boolean SHOW_BOT_CARDS = false;

    private AnchorPane tableAnchorPane;
    private VBox buttonsPane;
    private Button betRaiseButton;
    private Button callCheckButton;
    private Button foldButton;
    private Button back2menuButton;
    private Button newgameButton;
    private Pane player0Place;
    private Pane player1Place;
    private Pane player2Place;
    private Pane player3Place;
    private Pane player4Place;
    private Pane player5Place;
    private AnchorPane playersPane;
    private Label betLabel;
    private Slider betSlider;
    private HBox tableCardsBox;
    private Label potLabel;
    private Table table;
    private int currentBetValue;

    HashMap<Player, PlayerPane> playerToPane = new HashMap<>();

    MethodReferences references = new MethodReferences() {
        @Override
        public Consumer<Player> getFunctionToInformPlayerOfHisTurn() {
            return (Player p) -> new Thread(new Runnable() {
                @Override
                public void run() {
                    yourTurn(p);
                }
            }).start();
        }

        @Override
        public BiConsumer<Player, List<Card>> getFunctionToGivePlayersCards() {
            return (Player p, List<Card> cards) -> new Thread(new Runnable() {
                @Override
                public void run() {
                    giveCardsToPlayer(p, cards);
                }
            }).start();
        }

        @Override
        public Consumer<Card> getFunctionToAddCardToTable() {
            return (Card c) -> new Thread(new Runnable() {
                @Override
                public void run() {
                    addCardToTable(c);
                }
            }).start();
        }

        @Override
        public TriConsumer<Player, Actions, Integer> getFunctionToInformPlayersThatPlayerMadeMove() {
            return (Player p, Actions a, Integer i) -> new Thread(new Runnable() {
                @Override
                public void run() {
                    playerMadeMove(p, a, i);
                }
            }).start();
        }

        @Override
        public Command getFunctionToNewTurn() {
            return () -> new Thread(new Runnable() {

                @Override
                public void run() {
                    newTurn();
                }
            }).start();
        }

        @Override
        public Command getFunctionToEndHand() {
            return () -> new Thread(new Runnable() {
                @Override
                public void run() {
                    endHand();
                }
            }).start();
        }
    };
    
    public Game(Table table) {
    	
    	setTable(table);
    	
    	setPrefSize(1000, 666);
    	
    	tableAnchorPane = new AnchorPane();
		ImageView casinotable = new ImageView(ClassLoader.getSystemResource("table.png").toString());
		casinotable.setFitHeight(200);
		casinotable.setPreserveRatio(true);
		tableCardsBox = new HBox();
		potLabel = new Label();
		potLabel.setStyle("-fx-font-size: 18pt; -fx-text-fill: white;");
		
		tableAnchorPane.getChildren().addAll(casinotable,tableCardsBox,potLabel);
		tableAnchorPane.setTopAnchor(tableCardsBox, 40.0);
		tableAnchorPane.setLeftAnchor(tableCardsBox, 75.0);
		tableAnchorPane.setBottomAnchor(potLabel, 30.0);
		tableAnchorPane.setLeftAnchor(potLabel, 183.0);
		
		playersPane = new AnchorPane();
		playersPane.setPrefSize(750, 666);
		player0Place = new Pane();
		player1Place = new Pane();
		player2Place = new Pane();
		player3Place = new Pane();
		player4Place = new Pane();
		player5Place = new Pane();
		
		playersPane.getChildren().addAll(player0Place,player1Place,player2Place,player3Place,player4Place,player5Place);
		player0Place.setLayoutX(589);
		player0Place.setLayoutY(221);
		player1Place.setLayoutX(409);
		player1Place.setLayoutY(17);
		player2Place.setLayoutX(203);
		player2Place.setLayoutY(17);
		player3Place.setLayoutX(22);
		player3Place.setLayoutY(221);
		player4Place.setLayoutX(203);
		player4Place.setLayoutY(426);
		player5Place.setLayoutX(409);
		player5Place.setLayoutY(426);
		
    	String image = ClassLoader.getSystemResource("background.png").toString();
		ImageView bg = new ImageView(image);
		bg.setFitWidth(1000);
		bg.setPreserveRatio(true);
    	
    	VBox vbox = new VBox();
    	vbox.setPrefHeight(666);
    	vbox.setSpacing(10);
    	vbox.setAlignment(Pos.CENTER);
    	
    	betLabel = new Label();
    	betLabel.setFont(new Font(20));
    	betLabel.setStyle("-fx-text-fill: white;");
    	
    	betSlider = new Slider();
    	setSliderProporties();
    	
    	buttonsPane = new VBox();
    	
    	betRaiseButton = new Button();
    	callCheckButton =  new Button();
    	foldButton = new Button();
    	betRaiseButton.setStyle("-fx-background-color: transparent;");
    	foldButton.setStyle("-fx-background-color: transparent;");
    	callCheckButton.setStyle("-fx-background-color: transparent;");
    	
    	buttonsPane.getChildren().addAll(betLabel,betSlider,betRaiseButton,callCheckButton,foldButton);
    	buttonsPane.setAlignment(Pos.CENTER);
    	buttonsPane.setSpacing(10);
    	
    	Pane pane = new Pane();
    	pane.setPrefHeight(75);
    	
    	newgameButton = new Button();
    	setNewGameButton();
    	back2menuButton = new Button();
    	setBack2MenuButton();
    	
    	vbox.getChildren().addAll(buttonsPane,pane,newgameButton,back2menuButton);
    	
    	getChildren().addAll(bg,tableAnchorPane,playersPane,vbox);
    	setRightAnchor(vbox, 20.0);
    	setLeftAnchor(tableAnchorPane, 210.0);
    	setTopAnchor(tableAnchorPane, 240.0);
    	
    	startGame();
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void startGame() {
        table.setReferences(references);
        addPlayersToView();
        new Thread(table).start();
        tableCardsBox.getChildren().clear();
    }

    private void setSliderProporties() {
    	betSlider.setCursor(Cursor.OPEN_HAND);
    	betSlider.setShowTickLabels(true);
    	betSlider.setShowTickMarks(true);
        betSlider.setMajorTickUnit(100);
        betSlider.setBlockIncrement(20);
        betSlider.valueProperty()
                .addListener((obs, oldval, newVal) -> betSlider.setValue(Math.round(newVal.doubleValue())));
        betSlider.setMin(table.bigBlind);
        betSlider.setMax(table.getCurrentPlayer().numberOfChips);
        betSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                onSliderDrag();
            }
        });
    }

    private void giveCardsToPlayer(Player player, List<Card> cards) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (player instanceof ComputerPlayer && !SHOW_BOT_CARDS)
                    playerToPane.get(player).cardsBox.setCards(Arrays.asList(null, null));
                else
                    playerToPane.get(player).cardsBox.setCards(cards);
            }
        });
    }

    private void setButtonImage(Button button,String url) {
    	String image = ClassLoader.getSystemResource(url).toString();
		ImageView img = new ImageView(image);
		img.setFitWidth(120);
		img.setPreserveRatio(true);
    	button.setGraphic(img);
    }

    private void newTurn() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Player p : table.players) {
                    if (p.lastAction != Actions.All_IN)
                        playerToPane.get(p).clearActionLabelText();
                }
            }
        });
    }

    private void endHand() {

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Player p : table.players) {
                    if (!p.isFolded())
                        playerToPane.get(p).cardsBox.setCards(p.getHoleCards());
                }
            }
        });

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        table.removePlayersWithNoChips();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (Player p : table.players) {
                    playerToPane.get(p).setNumberOfChipsLabelText(p.numberOfChips);
                    playerToPane.get(p).clearActionLabelText();
                    playerToPane.get(p).setOpacity(1);
                    playerToPane.get(p).cardsBox.getChildren().clear();
                }

                for (Player d : playerToPane.keySet()) {
                    if (!table.players.contains(d))
                        playerToPane.get(d).setVisible(false);
                }
                tableCardsBox.getChildren().clear();
            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        table.startNewHand();
    }

    private void addCardToTable(Card card) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tableCardsBox.getChildren().add(new CardImageView(card, TABLE_CARD_HEIGHT));

            }
        });
    }


    private void addPlayersToView() {
        int index = 0;
        for (Player player : table.players) {
            Pane place = (Pane) playersPane.getChildren().get(index++);
            PlayerPane newPlayerPane = new PlayerPane(player.avatar, player);
            place.getChildren().add(newPlayerPane);
            playerToPane.put(player, newPlayerPane);
        }
    }

    private void BotTurn(ComputerPlayer computerplayer) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(BOT_WAIT_MILISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ((ComputerPlayer) computerplayer).makeAction();
                        } catch (ActionException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        }).start();
    }

    public void yourTurn(Player player) {

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                if (player instanceof ComputerPlayer && player.table.status != null) {
                	BotTurn((ComputerPlayer) player);
                    return;
                }

                buttonsPane.setVisible(true);

                Raise.MinMax minMax = Raise.getMinMaxRaiseValues(player);
                if (minMax != null) {
                    makeRaiseButton(minMax);
                } else {
                    makeBetButton(player);
                }

                if (new Call().isPossible(player)) {
                    makeCallButton();
                } else if (new Check().isPossible(player)) {
                    makeCheckButton();
                } else if (new AllIn().isPossible(player)) {
                    makeAllInButton();
                }

                if (new Fold().isPossible(player)) {
                    makeFoldButton();
                }
            }
        });

    }

    private void makeRaiseButton(Raise.MinMax minMax) {
    	betRaiseButton.setCursor(Cursor.HAND);
        setButtonImage(betRaiseButton, "raise1.png");
        betRaiseButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				playerRaised();
			}
		});
        betRaiseButton.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setButtonImage(betRaiseButton, "raise2.png");
				
			}
		});
        betRaiseButton.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setButtonImage(betRaiseButton, "raise1.png");
				
			}
		});
        betSlider.setMin(minMax.min);
        betSlider.setMax(minMax.max);
        betSlider.setValue(minMax.min);
    }

    private void makeBetButton(Player player) {
        betRaiseButton.setCursor(Cursor.HAND);
        setButtonImage(betRaiseButton, "bet1.png");
        betRaiseButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				playerBetted();
			}
		});
        betRaiseButton.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setButtonImage(betRaiseButton, "bet2.png");
				
			}
		});
        betRaiseButton.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setButtonImage(betRaiseButton, "bet1.png");
				
			}
		});
        betSlider.setMin(table.bigBlind);
        betSlider.setMax(player.numberOfChips);
        betSlider.setValue(table.bigBlind);
    }

    private void makeCallButton() {
        callCheckButton.setCursor(Cursor.HAND);
        setButtonImage(callCheckButton, "call1.png");
        callCheckButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				playerCalled();
			}
		});
        callCheckButton.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setButtonImage(callCheckButton, "call2.png");
				
			}
		});
        callCheckButton.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setButtonImage(callCheckButton, "call1.png");
				
			}
		});
    }

    private void makeCheckButton() {
        callCheckButton.setCursor(Cursor.HAND);
        setButtonImage(callCheckButton, "check1.png");
        callCheckButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				playerChecked();
			}
		});
        callCheckButton.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setButtonImage(callCheckButton, "check2.png");
				
			}
		});
        callCheckButton.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setButtonImage(callCheckButton, "check1.png");
				
			}
		});
    }

    private void playerMadeMove(Player player, Actions action, int value) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String text = action.toString();
                if (action == Actions.BET || action == Actions.RAISE || action == Actions.All_IN
                        || action == Actions.SMALL_BLIND || action == Actions.BIG_BLIND) {
                    text += " " + value;
                }
                playerToPane.get(player).setActionLabelText(text);
                playerToPane.get(player).setNumberOfChipsLabelText(player.numberOfChips);

                if (action == Actions.FOLD)
                    playerToPane.get(player).setOpacity(FOLD_OPACITY);

                potLabel.setText(Integer.toString(player.table.getNumberOfChipsOnTable()));
            }
        });
    }

    private void makeFoldButton() {
    	foldButton.setCursor(Cursor.HAND);
    	setButtonImage(foldButton, "fold1.png");
        foldButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				playerFolded();
			}
		});
        foldButton.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setButtonImage(foldButton, "fold2.png");
				
			}
		});
        foldButton.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setButtonImage(foldButton, "fold1.png");
				
			}
		});
    	
    }

    private void makeAllInButton() {
    	callCheckButton.setCursor(Cursor.HAND);
        setButtonImage(callCheckButton, "allin1.png");
        callCheckButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				playerAllIn();
			}
		});
        callCheckButton.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setButtonImage(callCheckButton, "allin2.png");
				
			}
		});
        callCheckButton.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setButtonImage(callCheckButton, "allin1.png");
				
			}
		});
    }

    private void playerRaised() {
        try {
            table.getCurrentPlayer().makeAction(Actions.RAISE, getRaiseValue());
            buttonsPane.setVisible(false);
        } catch (ActionException e) {
            e.printStackTrace();
        }
    }

    private void playerBetted() {
        try {
            table.getCurrentPlayer().makeAction(Actions.BET, getRaiseValue());
            buttonsPane.setVisible(false);
        } catch (ActionException e) {
            e.printStackTrace();
        }
    }

    private void playerCalled() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    table.getCurrentPlayer().makeAction(Actions.CALL);
                } catch (ActionException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        buttonsPane.setVisible(false);

    }

    private void playerChecked() {
        try {
            table.getCurrentPlayer().makeAction(Actions.CHECK);
            buttonsPane.setVisible(false);
        } catch (ActionException e) {
            e.printStackTrace();
        }
    }

    private void playerAllIn() {
        try {
            table.getCurrentPlayer().makeAction(Actions.All_IN);
            buttonsPane.setVisible(false);
        } catch (ActionException e) {
            e.printStackTrace();
        }
    }

    private void playerFolded() {
        try {
            table.getCurrentPlayer().makeAction(Actions.FOLD);
            buttonsPane.setVisible(false);
        } catch (ActionException e) {
            e.printStackTrace();
        }
    }

    private int getRaiseValue() {
        return currentBetValue;
    }

    private void onSliderDrag() {
        if ((int) betSlider.getValue() != currentBetValue) {
            String stringValue = Integer.toString((int) betSlider.getValue());
            betLabel.setText(stringValue);
            currentBetValue = (int) betSlider.getValue();
        }

    }


    
    private void setNewGameButton() {
    	setButtonImage(newgameButton, "newgame1.png");
    	newgameButton.setStyle("-fx-background-color: transparent;");
    	newgameButton.setCursor(Cursor.HAND);
    	newgameButton.setOnAction(new EventHandler<ActionEvent>() {

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
    	newgameButton.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setButtonImage(newgameButton, "newgame2.png");
				
			}
		});
    	newgameButton.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setButtonImage(newgameButton, "newgame1.png");
				
			}
		});
    	
    	
    }
    
    private void setBack2MenuButton() {
    	setButtonImage(back2menuButton, "backtomenu1.png");
    	back2menuButton.setStyle("-fx-background-color: transparent;");
    	back2menuButton.setCursor(Cursor.HAND);
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
    	back2menuButton.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setButtonImage(back2menuButton, "backtomenu2.png");
				
			}
		});
    	back2menuButton.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setButtonImage(back2menuButton, "backtomenu1.png");
				
			}
		});
    }

}