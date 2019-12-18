package Hands;

import java.util.ArrayList;
import java.util.Collections;

import Casino.Card;
import Casino.Hand;
import Casino.HandName;

public class OnePair extends HandFunction {
    {
        handName = HandName.ONE_PAIR;
    }

    @Override
    public Hand check(ArrayList<Card> cards) {
        if (cards == null || cards.size() < 5)
            throw new IllegalArgumentException();

        @SuppressWarnings("unchecked")
        ArrayList<Card> tempCards = (ArrayList<Card>) cards.clone();
        Collections.sort(tempCards, Collections.reverseOrder());
        ArrayList<Card> handCards = new ArrayList<>();

        for (int i = tempCards.size() - 1; i > 0; i--) {
            if (tempCards.get(i).getRank() == tempCards.get(i - 1).getRank()) {
                handCards.add(tempCards.get(i - 1));
                handCards.add(tempCards.get(i));
                tempCards.remove(i);
                tempCards.remove(i - 1);
                break;
            }
        }

        if (handCards.size() == 0)
            return null;

        ArrayList<Card> kickers = new ArrayList<>(tempCards.subList(0, 3));

        return new Hand(HandName.ONE_PAIR, handCards, kickers);
    }
}