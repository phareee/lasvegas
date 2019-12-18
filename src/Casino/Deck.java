package Casino;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class Deck implements Iterable<Card>{
	final static int DEFAULT_DECK_SIZE = 52;
    private ArrayList<Card> cards = new ArrayList<>();
    private int size;

    public int getSize() {
        return cards.size();
    }

    public Deck(int size) {
        if (size > DEFAULT_DECK_SIZE || size < 0 || size % 4 != 0) {
            throw new IllegalArgumentException("Size must be divisible by 4");
        }
        this.size = size;
        init();
    }

    public Deck() {
        this(DEFAULT_DECK_SIZE);
    }

    public void addCardsToDeck() {
        cards.clear();
        for (int i = Rank.values().length - 1; i >= Rank.values().length - size / 4; i--) {
            for (Suit suit : Suit.values()) {
                cards.add(new Card(Rank.values()[i], suit));
            }
        }
    }

    public Card peek() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return cards.get(cards.size() - 1);
    }

    public Card pop() {
        Card card = peek();
        cards.remove(cards.size() - 1);
        return card;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void init() {
        cards.clear();
        addCardsToDeck();
    }

	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}

}
