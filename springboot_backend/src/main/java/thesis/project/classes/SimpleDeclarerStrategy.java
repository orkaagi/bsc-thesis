package thesis.project.classes;

import org.springframework.data.util.Pair;

import java.util.*;

public class SimpleDeclarerStrategy extends Strategy {

    public SimpleDeclarerStrategy() {
        super();
    }

    /**
     * Ez a startégia nem számolja a kiment lapokat, ezért ez a metódus nem csinál semmit.
     *
     * @param seat  a jtáékos égtája
     * @param suit  szín
     * @param card  kártya értéke
     */
    public void updateObservedSuits(SeatName seat, SuitName suit, char card) {}

    /**
     * 1. helyen, ha
     * <ul>
     *     <li>van még adu az ellenfeleknél, akkor leaduzik: (kicsit hív a nagyobb felé, vagy) kihívja a legnagyobbat</li>
     *     <li>ha nincs adu az ellenfeleknél, akkor a preferált színből hív hasonlóan: (nagyobb felé, vagy) kihívja a legnagyobbat</li>
     *     <li>ha elfogyott a preferált szín, megkeresi a leghoszabbat és abból hív hasonlóan</li>
     * </ul>
     * @param hand  hand of the player (suits and their cards in a map)
     * @param trump trump of the gam
     * @return      the card to be played represented by its suit name as a string and its value as a character
     */
    protected Pair<String, Character> selectCardPos1(Map<SuitName, String> hand, SuitName trump, SeatName seat) {

        if (trump != SuitName.NOTRUMP && !hand.get(trump).isEmpty()){
            int opponentTrumpNumber = getObservedSuits().get(SeatName.EAST).get(trump).length() + getObservedSuits().get(SeatName.WEST).get(trump).length();
            if (opponentTrumpNumber < (13 - getInitialDeclarerTrumpNumber())) {
                SuitName name = trump;
                return Pair.of(name.name(), hand.get(name).charAt(0));
            }
        }
        if (!hand.get(getStrongestSuit()).isEmpty()) {
            SuitName name = getStrongestSuit();
            return Pair.of(name.name(), hand.get(name).charAt(0));
        }
        //setStrongestSuit(hand, dummy);
        SuitName name = findLongestSuit(hand);
        return Pair.of(name.name(), hand.get(name).charAt(0));
    }
}
