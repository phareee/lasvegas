package Utilities;

import Casino.Player;
import Casino.Rank;

public class RankPair {
	
	public Rank first;
    public Rank second;

    public RankPair(Rank first, Rank second) {
        this.first = first;
        this.second = second;
    }

    public RankPair(Player player) {
        if (player.getHoleCards().get(0).getRank().ordinal() > player.getHoleCards().get(1).getRank().ordinal()) {
            first = player.getHoleCards().get(0).getRank();
            second = player.getHoleCards().get(1).getRank();
        } else {
            first = player.getHoleCards().get(1).getRank();
            second = player.getHoleCards().get(0).getRank();
        }
    }

    @Override
    public boolean equals(Object a) {
        if (!(a instanceof RankPair))
            return false;
        return first.equals(((RankPair) a).first) && second.equals(((RankPair) a).second);
    }

    @Override
    public String toString() {
        return first + " " + second;
    }

    @Override
    public int hashCode() {
        return first.hashCode() + second.hashCode();
    }

}
