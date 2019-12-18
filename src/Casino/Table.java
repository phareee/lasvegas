package Casino;

import java.util.*;

import Actions.Actions;
import Exceptions.ActionException;
import Utilities.CircularList;

public class Table implements Runnable {

    private static final int FLOP_NUMBER_CARDS = 3;
    private static final int TURN_NUMBER_CARDS = 1;
    private static final int RIVER_NUMBER_CARDS = 1;

    public List<Player> players = new CircularList<>();
    public List<Card> tableCards = new ArrayList<>();
    public List<Pot> allPots = new ArrayList<>();
    public int dealerIndex;

    public List<Pot> currentTurnPots = new ArrayList<>();
    public Pot mainPot;

    public int maxBetInCurrentTurn;

    public int smallBlind;
    public int bigBlind;

    private boolean payingBlinds;

    public TableStatus status;

    private Deck deck = new Deck();
    private int currentIndex;

    private MethodReferences references;

    public Table() {
        references = new DefaultReferences();
    }

    public void setReferences(MethodReferences references) {
        this.references = references;
    }

    public Table(MethodReferences references) {
        this.references = references;
    }

    private boolean isHeadsUp() {
        return players.size() == 2;
    }

    public Player getCurrentPlayer() {
        return players.get(currentIndex);
    }

    public Player getSmallBlindPlayer() {
        return players.get(getSmallBlindIndex());
    }

    public Player getBigBlindPlayer() {
        return players.get(getBigBlindIndex());
    }

    private int getSmallBlindIndex() {
        return isHeadsUp() ? dealerIndex : dealerIndex + 1;
    }

    private int getBigBlindIndex() {
        return isHeadsUp() ? dealerIndex + 1 : dealerIndex + 2;
    }

    public void addCards(int number) {
        while (number-- > 0) {
            Card newCard = deck.pop();
            tableCards.add(newCard);
            references.getFunctionToAddCardToTable().accept(newCard);
        }
    }

    public void startGame() {
        dealerIndex = new Random().nextInt(players.size());
        startGame(dealerIndex);
    }

    public void startGame(int dealerIndex) {
        this.dealerIndex = dealerIndex - 1;
        startNewHand();
    }

    public void startNewHand() {
        if (players.size() == 1) {
            return;
        }

        removePlayersWithNoChips();
        removeAllCardsFromPlayers();

        deck.addCardsToDeck();
        deck.shuffle();
        giveCardsToPlayers();

        allPots.clear();
        currentTurnPots.clear();
        clearAllPlayersFlags();
        mainPot = null;
        maxBetInCurrentTurn = 0;

        dealerIndex = getNewDealerIndex();
        currentIndex = getSmallBlindIndex();

        payBlinds();
        status = TableStatus.PRE_FLOP;

        references.getFunctionToInformPlayerOfHisTurn().accept(getCurrentPlayer());
    }

    private void removeAllCardsFromPlayers() {
        for (Player player : players) {
            player.getHoleCards().clear();
        }
    }

    private void giveCardsToPlayers() {
        for (Player player : players) {
            player.getHoleCards().add(deck.pop());
            player.getHoleCards().add(deck.pop());
            references.getFunctionToGivePlayersCards().accept(player, player.getHoleCards());
        }
    }

    public void prepareNextMove() {
        Actions lastAction;
        if (payingBlinds)
            lastAction = getCurrentPlayer().lastAction == Actions.BET ? Actions.SMALL_BLIND : Actions.BIG_BLIND;
        else
            lastAction = getCurrentPlayer().lastAction;

        references.getFunctionToInformPlayersThatPlayerMadeMove().accept(getCurrentPlayer(), lastAction,
                getCurrentPlayer().lastBetRaiseValue);
        if (checkIfOnlyOnePlayerNotFolded()) {
            endHand();
            return;
        } else if (checkIfAllPlayersExceptOneAreAllIn()) {
            while (endTurn()) {
            }
            endHand();
            return;
        } else if (checkIfAllPlayersMakeActionThatAllowsToNextTurn()) {
            if (!endTurn()) {
                endHand();
                return;
            }
            startTurn();
        } else {
            currentIndex = getFirstPlayingPlayerIndexAfterCurrent();
        }

        if (!payingBlinds)
            references.getFunctionToInformPlayerOfHisTurn().accept(getCurrentPlayer());
    }

    // Return true if we are still at same hand, false when hand is over
    private boolean endTurn() {
        references.getFunctionToNewTurn().execute();

        if (status == null) {
            return false;
        }

        TableStatus nextStatus = getNextTableStatus();
        status = nextStatus;

        if (status == null) {
            return false;
        }

        switch (nextStatus) {
        case FLOP:
            addFlop();
            break;
        case TURN:
            addTurn();
            break;
        case RIVER:
            addRiver();
            break;
        default:
            throw new RuntimeException();
        }
        updatePlayersBestHand();
        clearAllPlayersLastAction();

        currentTurnPots.clear();
        mainPot = null;

        return true;
    }

    private void startTurn() {
        setUnFoldedPlayersPlayingInThisTurn();
        currentIndex = dealerIndex;
        currentIndex = getFirstPlayingPlayerIndexAfterCurrent();
    }

    private void clearAllPlayersFlags() {
        clearAllPlayersLastAction();
        for (Player player : players) {
            player.isPlaying = true;
            player.isPlayingThisTurn = true;
            player.isAllIn = false;
        }
    }

    private void setUnFoldedPlayersPlayingInThisTurn() {
        for (Player player : players) {
            if (player.isPlaying)
                player.isPlayingThisTurn = true;
        }
    }

    private void clearAllPlayersLastAction() {
        players.forEach(p -> p.lastAction = null);
    }

    private void updatePlayersBestHand() {
        for (Player player : players) {
            ArrayList<Card> playersAndTableCards = new ArrayList<>();
            playersAndTableCards.addAll(player.getHoleCards());
            playersAndTableCards.addAll(tableCards);
            player.currentBestHand = new Hand(playersAndTableCards);
        }
    }

    private void endHand() {
        giveWinningsToPlayers();

        references.getFunctionToEndHand().execute();
    }

    private void giveWinningsToPlayers() {
        for (Pot pot : allPots) {
            if (pot.players.size() == 0) {
                continue;
            }
            if (pot.players.size() == 1) {
                ((Player) pot.players.keySet().toArray()[0]).numberOfChips += pot.chips;
                continue;
            }
            TreeMap<Hand, Player> handToPlayer = new TreeMap<>();
            for (Player player : pot.players.keySet()) {
                handToPlayer.put(player.currentBestHand, player);
            }
            Player bestPlayer = handToPlayer.lastEntry().getValue();
            bestPlayer.numberOfChips += pot.chips;
        }
    }

    private TableStatus getNextTableStatus() {
        switch (status) {
        case PRE_FLOP:
            return TableStatus.FLOP;
        case FLOP:
            return TableStatus.TURN;
        case TURN:
            return TableStatus.RIVER;
        case RIVER:
            return null;
        default:
            throw new RuntimeException();
        }
    }

    private int getFirstPlayingPlayerIndexAfterCurrent() {
        for (int x = currentIndex + 1; x < players.size() + currentIndex; x++) {
            if (players.get(x).isPlaying) {
                return x;
            }
        }
        throw new RuntimeException();
    }

    private void addFlop() {
        addCards(FLOP_NUMBER_CARDS);
    }

    private void addTurn() {
        addCards(TURN_NUMBER_CARDS);
    }

    private void addRiver() {
        addCards(RIVER_NUMBER_CARDS);
    }

    private int getNewDealerIndex() {
        for (int x = dealerIndex + 1; x < players.size() + dealerIndex; x++) {
            if (players.get(x).numberOfChips > 0) {
                return x;
            }
        }
        // Only one player remaining
        throw new RuntimeException();
    }

    private void payBlinds() {
        payingBlinds = true;
        try {
            paySmallBlind();
            payBigBlind();
            maxBetInCurrentTurn = bigBlind;
            payingBlinds = false;
        } catch (ActionException e) {
            // This shouldn't happen - blinds players can always go allin (if chips > 0)
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    private void paySmallBlind() throws ActionException {
        Player smallBlindPlayer = getSmallBlindPlayer();
        if (smallBlindPlayer.numberOfChips <= smallBlind) {
            smallBlindPlayer.makeAction(Actions.All_IN);
        } else {
            int temp = bigBlind;
            bigBlind = smallBlind;
            smallBlindPlayer.makeAction(Actions.SMALL_BLIND, smallBlind);
            bigBlind = temp;
        }
    }

    private void payBigBlind() throws ActionException {
        Player bigBlindPlayer = getBigBlindPlayer();
        if (bigBlindPlayer.numberOfChips <= bigBlind) {
            bigBlindPlayer.makeAction(Actions.All_IN);
        } else {
            bigBlindPlayer.makeAction(Actions.BIG_BLIND, bigBlind);
            bigBlindPlayer.isPlayingThisTurn = true;
        }
    }

    public void removePlayersWithNoChips() {
        players.removeIf(p -> p.numberOfChips == 0);
    }

    private boolean checkIfOnlyOnePlayerNotFolded() {
        int numberOfPlayersNotFolded = 0;
        for (Player player : players) {
            if (player.isPlaying || player.isAllIn) {
                numberOfPlayersNotFolded++;
                if (numberOfPlayersNotFolded >= 2) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkIfAllPlayersMakeActionThatAllowsToNextTurn() {
        for (Player p : players) {
            if (p.isPlayingThisTurn)
                return false;
        }
        return true;
    }

    private boolean checkIfAllPlayersExceptOneAreAllIn() {
        int numberOfFoldedPlayers = 0;
        int numberOfAllInPlayers = 0;
        int numberOfCallPlayers = 0;
        for (Player player : players) {
            if (player.isFolded()) {
                numberOfFoldedPlayers++;
            } else if (player.isAllIn) {
                numberOfAllInPlayers++;
            } else if (player.lastAction == null || player.lastAction == Actions.BIG_BLIND) {
                return false;
            } else if (player.lastAction == Actions.CALL && !player.isPlayingThisTurn) {
                numberOfCallPlayers++;
            }
        }
        return (players.size() - numberOfFoldedPlayers - numberOfAllInPlayers - numberOfCallPlayers) == 0
                && numberOfCallPlayers <= 1;
    }

    public void setBlinds(int smallBlind, int bigBlind) {
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
    }

    public int getNumberOfChipsOnTable() {
        int wyn = 0;

        for (Pot p : allPots) {
            wyn += p.chips;
        }

        return wyn;
    }

    @Override
    public void run() {
        startGame();
    }
}