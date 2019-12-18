package Hands;

import java.util.ArrayList;
import java.util.Collections;

import Casino.Card;
import Casino.Hand;
import Casino.HandName;


public class TwoPair extends HandFunction {
    {
        handName = HandName.TWO_PAIR;
    }

    @Override
    public Hand check(ArrayList<Card> cards) {
        if (cards == null || cards.size() < 5)
            throw new IllegalArgumentException();

        @SuppressWarnings("unchecked")
        ArrayList<Card> sortedCards = (ArrayList<Card>) cards.clone();
        ArrayList<Card> handCards = new ArrayList<Card>();
        ArrayList<Card> kickers = new ArrayList<Card>();

        Collections.sort(sortedCards, Collections.reverseOrder());
        int numberOfPairs = 0;
        for (int i = 0; i < sortedCards.size() - 1 && numberOfPairs != 2; i++) {
            if (numberOfPairs != 2 && sortedCards.get(i).getRank() == sortedCards.get(i + 1).getRank()) {
                handCards.add(sortedCards.get(i));
                handCards.add(sortedCards.get(i + 1));
                sortedCards.remove(i + 1);
                sortedCards.remove(i);
                numberOfPairs++;
                i--;
            }
        }

        if (numberOfPairs != 2)
            return null;

        kickers.add(sortedCards.get(0));

        return new Hand(HandName.TWO_PAIR, handCards, kickers);
    }
}
