package Hands;

import java.util.ArrayList;
import java.util.Collections;

import Casino.Card;
import Casino.Hand;
import Casino.HandName;


public class ThreeOfAKind extends HandFunction {
    {
        handName = HandName.THREE_OF_A_KIND;
    }

    @Override
    public Hand check(ArrayList<Card> cards) {
        if (cards == null || cards.size() < 5)
            throw new IllegalArgumentException();

        @SuppressWarnings("unchecked")
        ArrayList<Card> sortedCards = (ArrayList<Card>) cards.clone();
        ArrayList<Card> handCards = new ArrayList<>();

        Collections.sort(sortedCards, Collections.reverseOrder());

        int counter = 1;
        for (int i = 0; i < sortedCards.size() - 1; i++) {
            if (sortedCards.get(i).getRank() == sortedCards.get(i + 1).getRank())
                counter++;
            else
                counter = 1;

            if (counter == 3) {
                handCards.addAll(sortedCards.subList(i - 1, i + 2));
                sortedCards.removeAll(sortedCards.subList(i - 1, i + 2));
                break;
            }
        }

        if (counter != 3)
            return null;

        ArrayList<Card> kickers = new ArrayList<>(sortedCards.subList(0, 2));

        return new Hand(HandName.THREE_OF_A_KIND, handCards, kickers);
    }
}
