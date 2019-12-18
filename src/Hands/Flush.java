package Hands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import Casino.Card;
import Casino.Hand;
import Casino.HandName;
import Casino.Suit;


public class Flush extends HandFunction {
	{
        handName = HandName.FLUSH;
    }

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
            if (suits.get(c.getSuit()).size() < 5) {
                suits.get(c.getSuit()).add(c);
            }
        }

        ArrayList<Card> maxSuit = null;
        for (ArrayList<Card> list : suits.values()) {
            if (list.size() == 5) {
                if (maxSuit == null) {
                    maxSuit = list;
                } else {
                    for (int i = 0; i < 5; i++) {
                        if (list.get(i).getRank().compareTo(maxSuit.get(i).getRank()) > 0) {
                            maxSuit = list;
                            break;
                        }
                        if (list.get(i).getRank().compareTo(maxSuit.get(i).getRank()) < 0) {
                            break;
                        }
                    }
                }
            }
        }

        if (maxSuit == null)
            return null;

        return new Hand(HandName.FLUSH, maxSuit, new ArrayList<Card>());
    }
}
