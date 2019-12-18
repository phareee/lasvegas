package Casino;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Actions.*;
import Exceptions.ActionException;
import Utilities.RankPair;

public class ComputerPlayer extends Player {
	private static final int preFlopHandStrengthValue = 50;

    private static final int flopHandStrengthValue = 15;
    private static final int flopBestHandValue = 25;
    private static final int flopHighestCardValue = 10;

    private static final int turnHandStrengthValue = 7;
    private static final int turnBestHandValue = 33;
    private static final int turnHighestCardValue = 10;

    private static final int riverBestHandValue = 40;
    private static final int riverHighestCardValue = 10;

    private static final int aggressionValue = 40;
    private static final int chipsValue = 10;

    private static Map<RankPair, Integer> HandStrength = new HashMap<>();

    static {
        HandStrength.put(new RankPair(Rank.ACE, Rank.ACE), 0);
        HandStrength.put(new RankPair(Rank.KING, Rank.KING), 0);
        HandStrength.put(new RankPair(Rank.QUEEN, Rank.QUEEN), 0);
        HandStrength.put(new RankPair(Rank.JACK, Rank.JACK), 0);
        HandStrength.put(new RankPair(Rank.ACE, Rank.KING), 0);

        HandStrength.put(new RankPair(Rank.TEN, Rank.TEN), 1);
        HandStrength.put(new RankPair(Rank.ACE, Rank.QUEEN), 1);
        HandStrength.put(new RankPair(Rank.ACE, Rank.JACK), 1);
        HandStrength.put(new RankPair(Rank.KING, Rank.QUEEN), 1);

        HandStrength.put(new RankPair(Rank.NINE, Rank.NINE), 2);
        HandStrength.put(new RankPair(Rank.ACE, Rank.TEN), 2);
        HandStrength.put(new RankPair(Rank.KING, Rank.JACK), 2);
        HandStrength.put(new RankPair(Rank.QUEEN, Rank.JACK), 2);
        HandStrength.put(new RankPair(Rank.JACK, Rank.TEN), 2);

        HandStrength.put(new RankPair(Rank.EIGHT, Rank.EIGHT), 3);
        HandStrength.put(new RankPair(Rank.KING, Rank.TEN), 3);
        HandStrength.put(new RankPair(Rank.QUEEN, Rank.TEN), 3);
        HandStrength.put(new RankPair(Rank.JACK, Rank.NINE), 3);
        HandStrength.put(new RankPair(Rank.TEN, Rank.NINE), 3);
        HandStrength.put(new RankPair(Rank.NINE, Rank.EIGHT), 3);

        HandStrength.put(new RankPair(Rank.ACE, Rank.NINE), 4);
        HandStrength.put(new RankPair(Rank.ACE, Rank.EIGHT), 4);
        HandStrength.put(new RankPair(Rank.ACE, Rank.SEVEN), 4);
        HandStrength.put(new RankPair(Rank.ACE, Rank.SIX), 4);
        HandStrength.put(new RankPair(Rank.ACE, Rank.FIVE), 4);
        HandStrength.put(new RankPair(Rank.ACE, Rank.FOUR), 4);
        HandStrength.put(new RankPair(Rank.ACE, Rank.THREE), 4);
        HandStrength.put(new RankPair(Rank.ACE, Rank.TWO), 4);
        HandStrength.put(new RankPair(Rank.QUEEN, Rank.NINE), 4);
        HandStrength.put(new RankPair(Rank.TEN, Rank.EIGHT), 4);
        HandStrength.put(new RankPair(Rank.EIGHT, Rank.SEVEN), 4);
        HandStrength.put(new RankPair(Rank.SEVEN, Rank.SEVEN), 4);
        HandStrength.put(new RankPair(Rank.SEVEN, Rank.SIX), 4);

        HandStrength.put(new RankPair(Rank.SIX, Rank.SIX), 5);
        HandStrength.put(new RankPair(Rank.FIVE, Rank.FIVE), 5);
        HandStrength.put(new RankPair(Rank.KING, Rank.NINE), 5);
        HandStrength.put(new RankPair(Rank.JACK, Rank.EIGHT), 5);
        HandStrength.put(new RankPair(Rank.EIGHT, Rank.SIX), 5);
        HandStrength.put(new RankPair(Rank.SEVEN, Rank.FIVE), 5);
        HandStrength.put(new RankPair(Rank.FIVE, Rank.FOUR), 5);

        HandStrength.put(new RankPair(Rank.FOUR, Rank.FOUR), 6);
        HandStrength.put(new RankPair(Rank.THREE, Rank.THREE), 6);
        HandStrength.put(new RankPair(Rank.TWO, Rank.TWO), 6);
        HandStrength.put(new RankPair(Rank.KING, Rank.EIGHT), 6);
        HandStrength.put(new RankPair(Rank.KING, Rank.SEVEN), 6);
        HandStrength.put(new RankPair(Rank.KING, Rank.SIX), 6);
        HandStrength.put(new RankPair(Rank.KING, Rank.FIVE), 6);
        HandStrength.put(new RankPair(Rank.KING, Rank.FOUR), 6);
        HandStrength.put(new RankPair(Rank.KING, Rank.THREE), 6);
        HandStrength.put(new RankPair(Rank.KING, Rank.TWO), 6);
        HandStrength.put(new RankPair(Rank.QUEEN, Rank.EIGHT), 6);
        HandStrength.put(new RankPair(Rank.TEN, Rank.SEVEN), 6);
        HandStrength.put(new RankPair(Rank.SIX, Rank.FOUR), 6);
        HandStrength.put(new RankPair(Rank.FIVE, Rank.THREE), 6);
        HandStrength.put(new RankPair(Rank.FOUR, Rank.THREE), 6);

        System.out.println(HandStrength);
    }

    private double aggression;

    public ComputerPlayer(Table table, double aggression) {
        this.table = table;
        this.aggression = aggression;
    }

    private int calculateStrength(RankPair myHand) {
        int strength = 0;

        switch (table.status) {
            case PRE_FLOP:
                strength = calculatePreFlopSrength(myHand);
                break;
            case FLOP:
                strength = calculateFlopSrength(myHand);
                break;
            case TURN:
                strength = calculateTurnSrength(myHand);
                break;
            case RIVER:
                strength = calculateRiverSrength();
        }

        strength += aggressionValue * aggression;
        strength += chipsValue * (1 - (Math.min(numberOfChipsNeededToCall(), numberOfChips) / (table.getNumberOfChipsOnTable())));

        return strength;
    }

    private int calculatePreFlopSrength(RankPair myHand) {
        return preFlopHandStrengthValue * (7 - HandStrength.get(myHand)) / 7;
    }

    private int calculateFlopSrength(RankPair myHand) {
        return (flopHandStrengthValue * (7 - HandStrength.get(myHand)) / 7) +
                (flopBestHandValue * currentBestHand.getHandName().ordinal() / 9) +
                (flopHighestCardValue * currentBestHand.getHandCards().get(0).getRank().ordinal() / 13);
    }

    private int calculateTurnSrength(RankPair myHand) {
        return (turnHandStrengthValue * (7 - HandStrength.get(myHand)) / 7) +
                (turnBestHandValue * currentBestHand.getHandName().ordinal() / 9) +
                (turnHighestCardValue * currentBestHand.getHandCards().get(0).getRank().ordinal() / 13);
    }

    private int calculateRiverSrength() {
        return (riverBestHandValue * currentBestHand.getHandName().ordinal() / 9) +
                (riverHighestCardValue * currentBestHand.getHandCards().get(0).getRank().ordinal() / 13);
    }

    private void makeAction(int strength) throws ActionException {
        if (strength <= 40) {
            makeCheckFoldAction(strength);
            return;
        }

        if (strength <= 60) {
            makeCallAllInAction(strength);
            return;
        }

        makeRaiseAllInAction(strength);
    }

    private void makeCheckFoldAction(int strength) throws ActionException {
        if (new Check().isPossible(this)) {
            makeAction(Actions.CHECK);
        } else {
            makeAction(Actions.FOLD);
        }
    }

    private void makeCallAllInAction(int strength) throws ActionException {
        if (new Call().isPossible(this)) {
            makeAction(Actions.CALL);
        } else if (new Check().isPossible(this)) {
            makeAction(Actions.CHECK);
        } else if(strength >= 55) {
            makeAction(Actions.All_IN);
        } else {
            makeAction(Actions.FOLD);
        }
    }

    private void makeRaiseAllInAction(int strength) throws ActionException {
        int howMany = new Random().nextInt(strength - 50) + Math.max(table.maxBetInCurrentTurn, table.bigBlind) * 2;

        if (new Raise(howMany).isPossible(this)) {
            makeAction(Actions.RAISE, howMany);
        } else if (new Bet(howMany).isPossible(this)) {
            makeAction(Actions.BET, howMany);
        } else if (strength >= 75 || !(new Call().isPossible(this))) {
            makeAction(Actions.All_IN);
        } else {
            makeAction(Actions.CALL);
        }
    }

    public void makeAction() throws ActionException {
        RankPair myHand = new RankPair(this);
        HandStrength.putIfAbsent(myHand, 7);

        makeAction(calculateStrength(myHand));
    }

}
