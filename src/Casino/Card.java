package Casino;

public class Card implements Comparable<Card>{
	private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        if (rank == null || suit == null) {
            throw new IllegalArgumentException();
        }
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public int compareTo(Card c) {
        if (rank != c.rank)
            return rank.compareTo(c.rank);

        return suit.compareTo(c.suit);
    }

    @Override
    public String toString() {
        return rank.toString() + " " + suit.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card)) {
            return false;
        }
        Card c = (Card) o;
        return rank == c.rank && suit == c.suit;
    }

}
