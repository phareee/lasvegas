package Hands;

import java.util.ArrayList;
import java.util.Collections;

import Casino.Card;
import Casino.Hand;
import Casino.HandName;


public class HighCard extends HandFunction {
    {
        handName = HandName.HIGH_CARD;
    }

    @Override
    public Hand check(ArrayList<Card> cards) {
        if (cards == null || cards.size() < 5)
            throw new IllegalArgumentException();

        @SuppressWarnings("unchecked")
        ArrayList<Card> tempCards = (ArrayList<Card>) cards.clone();

        Collections.sort(tempCards, Collections.reverseOrder());
        ArrayList<Card> handCards = new ArrayList<>();
        ArrayList<Card> kickers = new ArrayList<>();
        handCards.add(tempCards.get(0));

        for (int i = 1; i < 5; i++)
            kickers.add(tempCards.get(i));

        return new Hand(HandName.HIGH_CARD, handCards, kickers);
    }
}
