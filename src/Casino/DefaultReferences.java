package Casino;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import Actions.Actions;
import Utilities.Command;
import Utilities.TriConsumer;
public class DefaultReferences  implements MethodReferences{

	@Override
    public Consumer<Player> getFunctionToInformPlayerOfHisTurn() {
        return (Player p) -> {
        };
    }

	@Override
    public BiConsumer<Player, List<Card>> getFunctionToGivePlayersCards() {
        return (Player p, List<Card> c) -> {
        };
    }

	@Override
    public Consumer<Card> getFunctionToAddCardToTable() {
        return (Card c) -> {
        };
    }

	@Override
    public TriConsumer<Player, Actions, Integer> getFunctionToInformPlayersThatPlayerMadeMove() {
        return (Player p, Actions a, Integer i) -> {
        };
    }

	@Override
    public Command getFunctionToNewTurn() {
        return () -> {
        };
    }

    @Override
    public Command getFunctionToEndHand() {
        return () -> {
        };
    }
}

