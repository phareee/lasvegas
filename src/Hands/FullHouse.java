package Hands;

import java.util.ArrayList;
import java.util.Collections;

import Casino.Card;
import Casino.Hand;
import Casino.HandName;

public class FullHouse extends HandFunction {
    {
        handName = HandName.FULL_HOUSE;
    }

    public Hand check(ArrayList<Card> cards) {
        if (cards == null || cards.size() < 5)
            throw new IllegalArgumentException();

        @SuppressWarnings("unchecked")
        ArrayList<Card> sortedCards = (ArrayList<Card>) cards.clone();

        Collections.sort(sortedCards, Collections.reverseOrder());
        ArrayList<Card> maxPair = null;
        ArrayList<Card> maxThreeCards = null;

        for (int i = 0; i < sortedCards.size() - 1; i++) {
            if (maxThreeCards == null && i < sortedCards.size() - 2
                    && sortedCards.get(i).getRank() == sortedCards.get(i + 1).getRank()
                    && sortedCards.get(i).getRank() == sortedCards.get(i + 2).getRank()) {
                maxThreeCards = new ArrayList<Card>(sortedCards.subList(i, i + 3));
                i++;
                if (maxPair != null)
                    break;
                continue;
            }

            if (maxPair == null && sortedCards.get(i).getRank() == sortedCards.get(i + 1).getRank()) {
                maxPair = new ArrayList<Card>(sortedCards.subList(i, i + 2));
                if (maxThreeCards != null)
                    break;
            }
        }
        if (maxPair == null || maxThreeCards == null) {
            return null;
        }

        maxThreeCards.addAll(maxPair);

        return new Hand(HandName.FULL_HOUSE, maxThreeCards, new ArrayList<Card>());
    }
}
