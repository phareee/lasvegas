package Actions;

import Casino.Action;
import Casino.Player;
import Exceptions.ActionException;
import Exceptions.InvalidTableState;

public class Check extends Action {

    @Override
    public void make(Player player) throws ActionException {
        if (player.numberOfChipsNeededToCall() != 0) {
            throw new InvalidTableState();
        }

        player.lastAction = Actions.CHECK;
        
    }

    @Override
    public boolean isPossible(Player player) {
        if (!player.isPlaying) {
            return false;
        }

        if (player.numberOfChipsNeededToCall() != 0) {
            return false;
        }

        return true;
    }

}
