package thesis.project.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.util.Pair;

import java.util.*;

abstract public class Strategy {

    private Map<SeatName, Map<SuitName, String>> observedSuits;

    private Map<SuitName, String> dummyHand;

    private SuitName strongestSuit;

    private int initialDeclarerTrumpNumber = 0;

    public Strategy() { }

    @JsonIgnore
    public Map<SeatName, Map<SuitName, String>> getObservedSuits() {
        return this.observedSuits;
    }

    @JsonIgnore
    public Map<SuitName, String> getDummyHand() {
        return this.dummyHand;
    }

    @JsonIgnore
    public int getInitialDeclarerTrumpNumber() {
        return initialDeclarerTrumpNumber;
    }

    @JsonIgnore
    public SuitName getStrongestSuit() {
        return strongestSuit;
    }

    /**
     * Beállítja a(z adu utáni) legerősebb színt az alapján, hogy ebből mennyi van a vonalon (és hány pont van benne). Egyformán erős színek közül a magasabb rangút választja.
     * @param trump aduszín
     * @param hand  a felvevő keze
     * @param dummy az asztal keze
     */
    public void setStrongestSuit(SuitName trump, Map<SuitName, String> hand, Map<SuitName, String> dummy) {
        SuitName suit = SuitName.NOTRUMP;
        int maxLen = -1;
        for (SuitName s : hand.keySet()) {
            if(s!=trump && hand.get(s).length()+dummy.get(s).length() > maxLen) {
                maxLen = hand.get(s).length()+dummy.get(s).length();
                suit = s;
            }
        }
        this.strongestSuit = suit;
    }

    /**
     * Beállítja a felvevő vonalán található aduk számát (<code>initialDeclarerTrumpNumber</code> adattag) az átadott paraméterek alapján. Ha szanzadu játék van, nem változtatja meg az adattag alapértelmezett nulla értékét.
     * @param trump     adu színe
     * @param declarer  felvevő keze
     * @param dummy     asztal keze
     */
    public void initializeDeclarerTrumpNumber(SuitName trump, Map<SuitName, String> declarer, Map<SuitName, String> dummy) {
        if (trump != SuitName.NOTRUMP) {
            initialDeclarerTrumpNumber = declarer.get(trump).length() + dummy.get(trump).length();
        }
    }

    /**
     * Beállítja a <code>dummy</code> adattag értékét az átadott paraméter értékére.
     * @param dummy az asztal keze
     */
    public void initializeDummyHand(Map<SuitName, String> dummy) {
        this.dummyHand = dummy;
    }

    /**
     * Az ellenvonal kezeinek megfigyeléséhez üresre állítja az <code>observedSuits</code> adattag ellenvonal kezekhez tartozó értékét.
     * @param otherSeat1 az ellenvonal egyik égtája
     * @param otherSeat2 az ellenvonal másik égtája
     */
    public void initializeObservedSuits(SeatName otherSeat1, SeatName otherSeat2) {
        observedSuits = new HashMap<>();

        Map<SuitName, String> otherHand1 = new HashMap<>();
        otherHand1.put(SuitName.SPADES, "");
        otherHand1.put(SuitName.HEARTS, "");
        otherHand1.put(SuitName.DIAMONDS, "");
        otherHand1.put(SuitName.CLUBS, "");

        Map<SuitName, String> otherHand2 = new HashMap<>();
        otherHand2.put(SuitName.SPADES, "");
        otherHand2.put(SuitName.HEARTS, "");
        otherHand2.put(SuitName.DIAMONDS, "");
        otherHand2.put(SuitName.CLUBS, "");

        observedSuits.put(otherSeat1, otherHand1);
        observedSuits.put(otherSeat2, otherHand2);
    }

    /**
     * Meghívja a játékos aktuális körben elfoglalt pozíciójának megflelő lapkiválasztó metódust és visszatér ennek értékével.
     * Az 1. pozícióhoz tartozó <code>selectCardPos1</code> metódust mindegyik alosztály másképp valósítja meg. A 2. 3. és 4. pozícióhoz tartozó
     * <code>selectCardPos2</code> és <code>selectCardPos34</code> metódusok az osztály privát metódusai.
     *
     * @param seat  az égtáj, ahol a játékos ül
     * @param trump aduszín
     * @param hand  a játékos keze
     * @param round az aktuális állás
     * @return      a kiválasztott lappal
     */
    public Pair<String, Character> execute(SeatName seat, SuitName trump, Map<SuitName, String> hand, Round round) {

        if (round.getCurrentPosition() == round.getStarterPosition()) {
            return selectCardPos1(hand, trump, seat);
        }

        if (round.getCurrentPosition() == ((round.getStarterPosition() + 1) % 4)) {
            return selectCardPos2(hand, trump, round);
        }

        return selectCardPos34(hand, trump, round);

    }

    /**
     * Frissít a megfigyelt kiment lapokat (<code>observedSuit</code> adattag), azzal, hogy hozzáadja a paraméterben megadott égtáj megfigyelt lapjaihoz a paraméterben megadott kártyát.
     *
     * @param seat  a megfigyelt égtáj
     * @param suit  a megfigyelt kártya színe
     * @param card  a megfigyelt kártya értéke
     */
    abstract public void updateObservedSuits(SeatName seat, SuitName suit, char card);

    /**
     * Kiválaszt egy lapot, amit a játékos első helyen játszik ki.
     *
     * @param hand  a játékos keze
     * @param trump aduszín
     * @return      a kiválasztott lap
     */
    abstract protected Pair<String, Character> selectCardPos1(Map<SuitName, String> hand, SuitName trump, SeatName seat);

    /**
     * Kiválaszt egy lapot, amit a játékos második helyen játszik ki, az alábbi logika alapján:
     * <ul>
     *     <li>ha van induló színből, akkor fedni próbál a legkisebbel, ami még üt</li>
     *     <li>ha nincs az induló színből, de van adu, akkor alacsonyan lop figurát</li>
     *     <li>ha dobnia kell, akkor a legrövidebb színből dob</li>
     * </ul>
     * @param hand  a játékos keze
     * @param trump aduszín
     * @param round az aktuális állás
     * @return      a kiválasztott lap
     */
    private Pair<String, Character> selectCardPos2(Map<SuitName, String> hand, SuitName trump, Round round) {

        SuitName starterSuit = round.getLeadSuit();
        int starterCardValue = Round.transformCardToInt(round.getLeadCard());

        if (!hand.get(starterSuit).isEmpty()) {
            String suit = hand.get(starterSuit);

            if (starterCardValue > 9 && Round.transformCardToInt(suit.charAt(0)) > starterCardValue) {
                int ind = 0;
                while (ind < suit.length() && Round.transformCardToInt(suit.charAt(ind)) > starterCardValue) {
                    ind++;
                }
                return Pair.of(starterSuit.name(), suit.charAt(--ind));
            }

            return Pair.of(starterSuit.name(), suit.charAt(suit.length()-1));
        }
        else if (trump != SuitName.NOTRUMP && !hand.get(trump).isEmpty() && starterCardValue > 9) {
            String trumpSuit = hand.get(trump);
            return Pair.of(trump.name(), trumpSuit.charAt(trumpSuit.length()-1));
        }

        SuitName name = findShortestNonTrumpSuitIfPossible(hand, trump);
        String suit = hand.get(name);
        return Pair.of(name.name(), suit.charAt(suit.length()-1));
    }

    /**
     * 3. és 4. helyen, ha
     * <ul>
     *     <li>van az induló színből és eddig partner viszi, akkor kicsit tesz</li>
     *     <li>van az induló színből és eddig az ellenjátékos viszi, fedni próbál a legkisebb figurával, ami még üt</li>
     *     <li>nincs az induló színből, és eddig az ellenjátékos viszi akkor alacsonyan vagy magasan lop</li>
     *     <li>dobnia kell, akkor a legrövidebb színből dob</li>
     * </ul>
     * @param hand  a játékos keze
     * @param trump aduszín
     * @param round az aktuális állás
     * @return      a kiválasztott lap
     */
    private Pair<String, Character> selectCardPos34(Map<SuitName, String> hand, SuitName trump, Round round) {

        SuitName starterSuit = round.getLeadSuit();
        int winnerCardValue = round.getRoundWinner(trump).getSecond();

        if (!hand.get(starterSuit).isEmpty()) {
            String suit = hand.get(starterSuit);

            if (round.getRoundWinner(trump).getFirst() != SeatName.NORTH && round.getRoundWinner(trump).getFirst() != SeatName.SOUTH){

                int largestCard = Round.transformCardToInt(suit.charAt(0));
                if (largestCard > winnerCardValue && largestCard <= 10) {
                    return Pair.of(starterSuit.name(), suit.charAt(0));
                }
                else if (largestCard > winnerCardValue){
                    int ind = 0;
                    while (ind < suit.length() && Round.transformCardToInt(suit.charAt(ind)) > winnerCardValue && Round.transformCardToInt(suit.charAt(ind)) > 10) {
                        ind++;
                    }
                    return Pair.of(starterSuit.name(), suit.charAt(--ind));
                }
            }

            return Pair.of(starterSuit.name(), suit.charAt(suit.length()-1));

        }
        else if (trump != SuitName.NOTRUMP && !hand.get(trump).isEmpty()) {

            if (round.getRoundWinner(trump).getFirst() != SeatName.NORTH && round.getRoundWinner(trump).getFirst() != SeatName.SOUTH) {
                String trumpSuit = hand.get(trump);
                int maxTrumpValue = Round.transformCardToInt(trumpSuit.charAt(0))+13;

                if (maxTrumpValue > winnerCardValue) {
                    int ind = 0;
                    while (ind < trumpSuit.length() && Round.transformCardToInt(trumpSuit.charAt(ind))+13 > winnerCardValue) {
                        ind++;
                    }
                    return Pair.of(trump.name(), trumpSuit.charAt(--ind));
                }
            }

        }

        SuitName name = findShortestNonTrumpSuitIfPossible(hand, trump);
        String suit = hand.get(name);
        return Pair.of(name.name(), suit.charAt(suit.length()-1));
    }

    /**
     * Visszatér a kézben tartott legrövidebb, nem sikén színnel.
     * @return  legrövidebb szín neve
     */
    private SuitName findShortestNonEmptySuit(Map<SuitName, String> hand){
        SuitName suit = SuitName.NOTRUMP;
        int minLen = 14;
        for (SuitName s : hand.keySet()) {
            if(!hand.get(s).isEmpty() && hand.get(s).length() < minLen) {
                minLen = hand.get(s).length();
                suit = s;
            }
        }
        return suit;
    }

    /**
     * Visszatér a kézben tartott legrövidebb, nem sikén színnel, ami nem adu, hacsak nem az adu az eygetlen színe.
     * @return  legrövidebb szín neve
     */
    protected SuitName findShortestNonTrumpSuitIfPossible(Map<SuitName, String> hand, SuitName trump){
        SuitName name = findShortestNonEmptySuit(hand);
        if (name == trump) {
            Map<SuitName, String> modifiedHand = hand;
            modifiedHand.remove(trump);
            SuitName modifiedName = findShortestNonEmptySuit(modifiedHand);
            if (modifiedName != SuitName.NOTRUMP) {
                name = modifiedName;
            }
        }
        return name;
    }

    /**
     * Visszatér a kézben tartott leghosszabb színnel.
     * @return  leghosszabb szín neve
     */
    protected SuitName findLongestSuit(Map<SuitName, String> hand){
        SuitName suit = SuitName.NOTRUMP;
        int maxLen = -1;
        for (SuitName s : hand.keySet()) {
            if(hand.get(s).length() > maxLen) {
                maxLen = hand.get(s).length();
                suit = s;
            }
        }
        return suit;
    }
}
