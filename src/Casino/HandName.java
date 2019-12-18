package Casino;

import java.util.ArrayList;

import Hands.*;

public enum HandName {
	HIGH_CARD {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new HighCard().check(cards);
        }

        @Override
        public String toString() {
            return "High card";
        }
    },

    ONE_PAIR {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new OnePair().check(cards);
        }

        @Override
        public String toString() {
            return "One pair";
        }
    },

    TWO_PAIR {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new TwoPair().check(cards);
        }

        @Override
        public String toString() {
            return "Two pair";
        }
    },

    THREE_OF_A_KIND {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new ThreeOfAKind().check(cards);
        }

        @Override
        public String toString() {
            return "Three of a kind";
        }
    },

    STRAIGHT {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new Straight().check(cards);
        }

        @Override
        public String toString() {
            return "Straight";
        }
    },

    FLUSH {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new Flush().check(cards);
        }

        @Override
        public String toString() {
            return "Flush";
        }
    },

    FULL_HOUSE {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new FullHouse().check(cards);
        }

        @Override
        public String toString() {
            return "Full house";
        }
    },

    FOUR_OF_A_KIND {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new FourOfAKind().check(cards);
        }

        @Override
        public String toString() {
            return "Four of a kind";
        }
    },

    STRAIGHT_FLUSH {
        @Override
        public Hand checkMe(ArrayList<Card> cards) {
            return new StraightFlush().check(cards);
        }

        @Override
        public String toString() {
            return "Straight flush";
        }
    };

    public abstract Hand checkMe(ArrayList<Card> cards);
}

