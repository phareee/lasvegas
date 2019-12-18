package Casino;

import java.util.ArrayList;
import java.util.List;

import Casino.Card;
import Casino.Hand;
import Casino.Pot;
import Casino.Table;
import Actions.Actions;
import Exceptions.ActionException;
import javafx.scene.image.Image;

public class Player {
	public boolean isPlaying = false;
    public boolean isAllIn = false;
    public boolean isPlayingThisTurn = false;

    public Actions lastAction;
    public Table table;

    public Hand currentBestHand;

    public int numberOfChips;

    public Image avatar;

    private List<Card> holeCards = new ArrayList<>();
	public int lastBetRaiseValue;

    public Player() {}

    public Player(Table table) {
        this.table = table;
    }

    public boolean isFolded() {
        return !isPlaying && !isAllIn;
    }

    public int getNumberOfChips() {
        return numberOfChips;
    }

    public List<Card> getHoleCards() {
        return holeCards;
    }

    public void makeAction(Actions action, int arg) throws ActionException {
        action.make(this, arg);
    }

    public void makeAction(Actions action) throws ActionException {
        action.make(this);
    }

    public int numberOfChipsNeededToCall() {
        int chipsNeeded = 0;

        for (Pot p : table.currentTurnPots) {
            if (p.players.get(this) == null) {
                chipsNeeded += p.maxBet;
                continue;
            }
            if (p.players.get(this) < p.maxBet) {
                chipsNeeded += p.maxBet - p.players.get(this);
            }
        }

        return chipsNeeded;
    }


}
