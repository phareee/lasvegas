package Casino;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import Actions.Actions;
import Utilities.Command;
import Utilities.TriConsumer;

public interface MethodReferences {
	public Consumer<Player> getFunctionToInformPlayerOfHisTurn();

    public BiConsumer<Player, List<Card>> getFunctionToGivePlayersCards();

    public Consumer<Card> getFunctionToAddCardToTable();

    public TriConsumer<Player, Actions, Integer> getFunctionToInformPlayersThatPlayerMadeMove();

    public Command getFunctionToNewTurn();

    public Command getFunctionToEndHand();
}