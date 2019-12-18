package Hands;

import Casino.Card;
import Casino.Hand;
import Casino.HandName;
import Casino.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class StraightFlush extends HandFunction {
    {
        handName = HandName.STRAIGHT_FLUSH;
    }

    @Override
    public Hand check(ArrayList<Card> cards) {
        if (cards == null || cards.size() < 5)
            throw new IllegalArgumentException();

        @SuppressWarnings("unchecked")
        ArrayList<Card> sortedCards = (ArrayList<Card>) cards.clone();

        Collections.sort(sortedCards, Collections.reverseOrder());

        HashMap<Suit, ArrayList<Card>> suits = new HashMap<>();
        for (Suit s : Suit.values()) {
            suits.put(s, new ArrayList<Card>());
        }

        for (Card c : sortedCards) {
            suits.get(c.getSuit()).add(c);
        }

        Straight straight = new Straight();

        for (Suit s : Suit.values()) {
            if (suits.get(s).size() >= 5) {
                ArrayList<Card> suitCards = suits.get(s);
                Hand h = straight.check(suitCards);
                if (h != null) {
                    suits.replace(s, h.getHandCards());
                } else {
                    suits.replace(s, new ArrayList<>());
                }
            }
        }

        ArrayList<Card> maxSuit = null;
        for (ArrayList<Card> list : suits.values()) {
            if (list.size() == 5) {
                if (maxSuit == null) {
                    maxSuit = list;
                } else {
                    for (int i = 0; i < 5; i++) {
                        int compare = list.get(i).getRank().compareTo(maxSuit.get(i).getRank());
                        if (compare > 0) {
                            maxSuit = list;
                            break;
                        }
                        if (compare < 0) {
                            break;
                        }
                    }
                }
            }
        }

        if (maxSuit == null)
            return null;

        return new Hand(HandName.STRAIGHT_FLUSH, maxSuit, new ArrayList<Card>());
    }
}
