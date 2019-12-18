package Hands;

import Casino.Card;
import Casino.Hand;
import Casino.HandName;
import Casino.Rank;

import java.util.ArrayList;
import java.util.Collections;

public class Straight extends HandFunction {
    {
        handName = HandName.STRAIGHT;
    }

    @Override
    public Hand check(ArrayList<Card> cards) {
        if (cards == null || cards.size() < 5)
            throw new IllegalArgumentException();

        @SuppressWarnings("unchecked")
        ArrayList<Card> sortedCards = (ArrayList<Card>) cards.clone();

        Collections.sort(sortedCards, Collections.reverseOrder());
        ArrayList<Card> handCards = new ArrayList<>();
        handCards.add(sortedCards.get(0));

        for (Card c : sortedCards) {
            if (handCards.get(handCards.size() - 1).getRank() == c.getRank()) {
                continue;
            }
            if (handCards.get(handCards.size() - 1).getRank().ordinal() - c.getRank().ordinal() == 1) {
                handCards.add(c);
                if (handCards.size() == 5)
                    break;
            } else {
                handCards.clear();
                handCards.add(c);
            }
        }

        if (handCards.size() == 4 && handCards.get(handCards.size() - 1).getRank() == Rank.TWO
                && sortedCards.get(0).getRank() == Rank.ACE) {
            handCards.add(sortedCards.get(0));
        }

        if (handCards.size() < 5)
            return null;

        return new Hand(HandName.STRAIGHT, handCards, new ArrayList<Card>());
    }
}
