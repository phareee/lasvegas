package Casino;

import java.util.ArrayList;

public class Hand implements Comparable<Hand> {

    private HandName handName;
    private ArrayList<Card> handCards;
    private ArrayList<Card> kickers;

    @SuppressWarnings("unchecked")
    public Hand(HandName handName, ArrayList<Card> handCards, ArrayList<Card> kickers) {
        if (handName == null || handCards == null || kickers == null) {
            throw new IllegalArgumentException();
        }

        if (handCards.size() + kickers.size() != 5) {
            throw new IllegalArgumentException("Hand must contain exactly 5 cards");
        }

        this.handName = handName;
        this.handCards = (ArrayList<Card>) handCards.clone();
        this.kickers = (ArrayList<Card>) kickers.clone();
    }

    public Hand(ArrayList<Card> cards) {
        Hand h = checkHand(cards);
        kickers = h.kickers;
        handCards = h.handCards;
        handName = h.handName;
    }

    public static boolean checkHandToGiven(ArrayList<Card> cards, HandName given) {
        for (int i = HandName.values().length - 1; i > given.ordinal(); i--) {
            Hand h = HandName.values()[i].checkMe(cards);
            if (h != null)
                return true;
        }

        return false;
    }

    public static Hand checkHand(ArrayList<Card> cards) {
        for (int i = HandName.values().length - 1; i >= 0; i--) {
            Hand h = HandName.values()[i].checkMe(cards);
            if (h != null)
                return h;
        }

        throw new IllegalArgumentException();
    }

    public HandName getHandName() {
        return handName;
    }

    public ArrayList<Card> getHandCards() {
        return handCards;
    }

    public ArrayList<Card> getKickers() {
        return kickers;
    }

    @Override
    public int compareTo(Hand h) {
        if (handName != h.handName)
            return handName.compareTo(h.handName);

        for (int i = 0; i < handCards.size() - 1; i++) {
            if (handCards.get(i).getRank() != h.handCards.get(i).getRank())
                return handCards.get(i).getRank().compareTo(h.handCards.get(i).getRank());
        }

        for (int i = 0; i < kickers.size(); i++) {
            if (kickers.get(i).getRank() != h.kickers.get(i).getRank())
                return kickers.get(i).getRank().compareTo(h.kickers.get(i).getRank());
        }

        return 0;
    }

    @Override
    public String toString() {
        return handName + handCards.toString() + kickers.toString();
    }
}

