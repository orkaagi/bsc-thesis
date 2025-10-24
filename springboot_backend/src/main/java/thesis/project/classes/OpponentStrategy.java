package thesis.project.classes;

import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpponentStrategy extends Strategy {

    public OpponentStrategy() {
        super();
    }

    public void updateObservedSuits(SeatName seat, SuitName suit, char card) {}

    // kikommentelve: legkisebbet
    // mi szamit hosszu adunak?
    /**
     * 1. helyen, ha
     * <ul>
     *     <li>hosszu au aduszine, akkor hosszu szinbol hiv, figurat, ha van (kozoluk a legnagyobbat), kicsit, ha nincs</li>
     *     <li>rövid az aduszine, akkor rövid színből hív</li>
     * </ul>
     * @param hand  a játékos keze
     * @param trump aduszín a játékban
     * @return      a kijátszandó lap
     */
    protected Pair<String, Character> selectCardPos1(Map<SuitName, String> hand, SuitName trump, SeatName seat) {

        if (trump == SuitName.NOTRUMP) {
            SuitName longSuit = findLongestSuit(hand);
            String longSuitCards = hand.get(longSuit);
            if (!longSuitCards.isEmpty()) {
                int maxValue = Round.transformCardToInt(longSuitCards.charAt(0));
                if (maxValue > 10) {
                    /*int ind = 0;
                    while (ind < strongSuit.length() && Round.transformCardToInt(strongSuit.charAt(ind)) > 9) {
                        ind++;
                    }*/
                    return Pair.of(longSuit.name(), longSuitCards.charAt(0));
                }
                return Pair.of(longSuit.name(), longSuitCards.charAt(longSuitCards.length() - 1));
            }
        }
        if (hand.get(trump).length() > 2) {
            SuitName longestSuit = findLongestSuit(hand);
            String suit = hand.get(longestSuit);
            int maxValue = Round.transformCardToInt(suit.charAt(0));
            if (maxValue > 10) {
            /*int ind = 0;
            while (ind < strongSuit.length() && Round.transformCardToInt(strongSuit.charAt(ind)) > 9) {
                ind++;
            }*/
                return Pair.of(longestSuit.name(), suit.charAt(0));
            }
            return Pair.of(longestSuit.name(), suit.charAt(suit.length() - 1));
        }
        SuitName shortestSuit = findShortestNonTrumpSuitIfPossible(hand, trump);
        String suit = hand.get(shortestSuit);
        return Pair.of(shortestSuit.name(), suit.charAt(suit.length() - 1));
    }

}
