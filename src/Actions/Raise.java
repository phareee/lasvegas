package Actions;

import Casino.Action;
import Casino.Player;
import Casino.Pot;
import Exceptions.ActionException;
import Exceptions.InvalidTableState;
import Exceptions.NotEnoughChips;

public class Raise extends Action {

    private int howMany;

    public Raise(int howMany) {
        this.howMany = howMany;
    }

    @Override
    public void make(Player player) throws ActionException {
        if (player.table.currentTurnPots.size() == 0) {
            throw new InvalidTableState();
        }

        if (howMany < player.table.maxBetInCurrentTurn * 2) {
            throw new InvalidTableState();
        }

        player.lastBetRaiseValue = howMany;

        for (Pot p : player.table.currentTurnPots) {
            howMany -= p.maxBet;
        }

        if (player.numberOfChipsNeededToCall() + howMany > player.numberOfChips) {
            throw new NotEnoughChips();
        }

        if (player.numberOfChipsNeededToCall() + howMany == player.numberOfChips) {
            new AllIn().make(player);
            return;
        }

        for (Pot p : player.table.currentTurnPots) {
            p.players.putIfAbsent(player, 0);
            p.chips += p.maxBet - p.players.get(player);
            player.numberOfChips -= p.maxBet - p.players.get(player);
            p.players.replace(player, p.maxBet);
        }

        if (player.table.mainPot == null) {
            player.table.mainPot = new Pot();
            player.table.allPots.add(player.table.mainPot);
        }

        player.table.maxBetInCurrentTurn += howMany;
        player.table.mainPot.chips += howMany;
        player.table.mainPot.maxBet += howMany;
        player.table.mainPot.players.put(player, player.table.mainPot.maxBet);
        player.numberOfChips -= howMany;

        for (Player p : player.table.players) {
            if (p.isPlaying)
                p.isPlayingThisTurn = true;
        }

        player.lastAction = Actions.RAISE;
    }

    @Override
    public boolean isPossible(Player player) {
        if (player.table.currentTurnPots.size() == 0) {
            return false;
        }

        if (howMany < player.table.maxBetInCurrentTurn * 2) {
            return false;
        }

        for (Pot p : player.table.currentTurnPots) {
            howMany -= p.maxBet;
        }

        if (player.numberOfChipsNeededToCall() + howMany >= player.numberOfChips) {
            return false;
        }

        return true;
    }

    public static MinMax getMinMaxRaiseValues(Player player) {
        if (player.table.currentTurnPots.size() == 0) {
            return null;
        }

        return new MinMax(Math.max(player.table.maxBetInCurrentTurn, player.table.bigBlind) * 2,
                player.numberOfChips - player.numberOfChipsNeededToCall() + player.table.maxBetInCurrentTurn);
    }

    public static class MinMax {
        public int min;
        public int max;

        public MinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }
}
