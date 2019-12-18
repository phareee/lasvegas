package Casino;

import java.util.HashMap;
import java.util.Map;

public class Pot {
    public Map<Player, Integer> players = new HashMap<>();
    public int chips = 0;
    public int maxBet = 0;
}