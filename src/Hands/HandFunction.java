package Hands;
import Casino.Card;
import Casino.Hand;
import Casino.HandName;

import java.util.ArrayList;

import static Casino.Hand.checkHandToGiven;

public abstract class HandFunction {
    HandName handName;

    abstract Hand check(ArrayList<Card> cards);

    public Hand apply(ArrayList<Card> c) {
        if (checkHandToGiven(c, handName)) {
            return null;
        }
        return check(c);
    }
}
