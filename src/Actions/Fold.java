package Actions;

import Casino.Action;
import Casino.Player;
import Casino.Pot;
import Exceptions.ActionException;

public class Fold extends Action {

    @Override
    public void make(Player player) throws ActionException {
        for (Pot p : player.table.allPots) {
            p.players.remove(player);
        }

        player.isPlaying = false;

        player.lastAction = Actions.FOLD;
        
    }

    @Override
    public boolean isPossible(Player player) {
        return player.isPlaying;
    }

}
