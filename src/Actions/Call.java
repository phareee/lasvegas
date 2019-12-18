package Actions;

import Casino.Action;
import Casino.Player;
import Casino.Pot;
import Exceptions.ActionException;
import Exceptions.InvalidTableState;
import Exceptions.NotEnoughChips;

public class Call extends Action {

    @Override
    public void make(Player player) throws ActionException {
        if (player.table.currentTurnPots.size() == 0) {
            throw new InvalidTableState();
        }

        if (player.numberOfChipsNeededToCall() > player.numberOfChips) {
            throw new NotEnoughChips();
        }

        if (player.numberOfChipsNeededToCall() == 0) {
            throw new InvalidTableState();
        }

        for (Pot p : player.table.currentTurnPots) {
            p.players.putIfAbsent(player, 0);
            p.chips += p.maxBet - p.players.get(player);
            player.numberOfChips -= p.maxBet - p.players.get(player);
            p.players.replace(player, p.maxBet);
        }

        player.lastAction = Actions.CALL;

    }

    @Override
    public boolean isPossible(Player player) {
        if (player.table.currentTurnPots.size() == 0) {
            return false;
        }

        if (player.numberOfChipsNeededToCall() >= player.numberOfChips) {
            return false;
        }

        if (player.numberOfChipsNeededToCall() == 0) {
            return false;
        }

        return true;
    }

}
