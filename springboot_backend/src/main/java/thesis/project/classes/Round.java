package thesis.project.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class Round {

    private static final Map<SeatName, Integer> positions;

    private int starterPosition = 0;

    private int currentPosition = 0;

    private boolean isNewRound = true;

    private Pair<String, Character>[] cards = new Pair[4];

    static {
        positions = new HashMap<>();
        positions.put(SeatName.EAST, 0);
        positions.put(SeatName.SOUTH, 1);
        positions.put(SeatName.WEST, 2);
        positions.put(SeatName.NORTH, 3);
    }

    public Round() {
        cards[0] = Pair.of("", ' ');
        cards[1] = Pair.of("", ' ');
        cards[2] = Pair.of("", ' ');
        cards[3] = Pair.of("", ' ');
    }

    public Round(int starterPosition, int currentPosition, Pair<String, Character>[] cards) {
        this.starterPosition = starterPosition;
        this.currentPosition = currentPosition;
        this.cards = cards;
    }

    public Map<SeatName, Integer> getPositions() {
        return positions;
    }

    @JsonProperty("starterIndex")
    public int getStarterPosition() {
        return starterPosition;
    }

    @JsonProperty("currentIndex")
    public int getCurrentPosition() {
        return currentPosition;
    }

    public boolean getIsNewRound() {
        return isNewRound;
    }

    public void setIsNewRound(boolean newValue){
        isNewRound = newValue;
    }

    @JsonProperty("round")
    public Pair<String, Character>[] getCards() {
        return cards;
    }

    @JsonIgnore
    public SuitName getLeadSuit() {
        return SuitName.valueOf(cards[starterPosition].getFirst());
    }

    @JsonIgnore
    public char getLeadCard() {
        return cards[starterPosition].getSecond();
    }

    /**
     * A paraméterben megadott kártya értékéhez egy egész szám értékét rendeli 2 és 14 között.
     * @param card a kártya eredeti értéke
     * @return  a kártya értéke egész számmal kifejezve
     */
    public static int transformCardToInt(char card) {
        return switch (card) {
            case 'A' -> 14;
            case 'K' -> 13;
            case 'Q' -> 12;
            case 'J' -> 11;
            case 'T' -> 10;
            case ' ' -> -1;
            default -> Character.getNumericValue(card);
        };
    }

    /**
     * Üres sztringre és üres karakterre állítja a <code>cards</code> tömb értékeit.
     */
    public void resetCards() {
        cards[0] = Pair.of("", ' ');
        cards[1] = Pair.of("", ' ');
        cards[2] = Pair.of("", ' ');
        cards[3] = Pair.of("", ' ');
    }

    /**
     * Frissíti az aktuális állást (<code>cards</code> adattagot) a paraméterben átadott kártyával, ha annak kijátszása megfelel a bridzs szabályainak.
     *
     * @param name  a lapot kijátszani kívánó játékos égtája
     * @param suit  szín, amiből a játékos lapot kíván kijátszani
     * @param card  kártya értéke, amit a játékos ki kíván játszani
     * @param isVoid    logikai érték, ami akkor igaz, ha a játékosnak a kör elején hívott sznből sikénje van
     * @param trump aduszín
     * @return  üzenet a lapkijátszás érvényességéről
     */
    public String addToCards(SeatName name, String suit, char card, boolean isVoid, SuitName trump) {
        if(positions.get(name) == currentPosition) {

            // jo szinbol tesz-e (ha nem --> warning)
            if(isVoid || isNewRound || cards[starterPosition].getFirst().equals(suit)) {
                cards[currentPosition] = Pair.of(suit, card);

                // soron kovetkezo jatekos indexenek frissitese
                currentPosition = (currentPosition + 1) % 4;

                // ha korbeertunk, kiertekeljuk a kort
                if(currentPosition == starterPosition) {
                    return evaluateRound(trump);
                }

                isNewRound = false;
                return "Add completed";
            }
            return "Invalid suit";
        }
        return "Invalid player";
    }


    /**
     * Visszatér az adott állást vivő játékos égtájával és a legnagyobb kártya értékével, ahol a kártya értékét a <code>transformCardToInt</code> metódus számítja ki, és az adu értékéhez hozáad 13-at.
     *
     * @param trump  az adu színe
     * @return  az adott állást vivő játékos égtájával és a legnagyobb kártya értékével
     */
    @JsonIgnore
    public Pair<SeatName, Integer> getRoundWinner(SuitName trump) {
        String originalSuit = getLeadSuit().name();

        int maxVal = -1;
        int maxInd = -1;
        int ind = 0;

        for(Pair<String, Character> p : cards){
            if (!p.getFirst().isEmpty()) {
                boolean isLargestTrump = (SuitName.valueOf(p.getFirst()) == trump && transformCardToInt(p.getSecond())+13 > maxVal);
                boolean isLargestFromStarterSuit = (p.getFirst().equals(originalSuit) && transformCardToInt(p.getSecond()) > maxVal);

                if (isLargestTrump || isLargestFromStarterSuit) {
                    maxVal = isLargestTrump ? transformCardToInt(p.getSecond())+13 : transformCardToInt(p.getSecond());
                    maxInd = ind;
                }

            }
            ind++;

        }

        SeatName winner =
                switch (maxInd) {
                    case 0 -> SeatName.EAST;
                    case 1 -> SeatName.SOUTH;
                    case 2 -> SeatName.WEST;
                    case 3 -> SeatName.NORTH;
                    default -> SeatName.NORTH;
                };

        return Pair.of(winner, maxVal);
    }

    private String evaluateRound(SuitName trump) {
        String originalSuit = cards[starterPosition].getFirst();

        int maxVal = -1;
        int maxInd = -1;
        int ind = 0;

        for(Pair<String, Character> p : cards){

            boolean isLargestTrump = (SuitName.valueOf(p.getFirst()) == trump && transformCardToInt(p.getSecond())+13 > maxVal);
            boolean isLargestFromStarterSuit = (p.getFirst().equals(originalSuit) && transformCardToInt(p.getSecond()) > maxVal);

            if (isLargestTrump || isLargestFromStarterSuit) {
                maxVal = isLargestTrump ? transformCardToInt(p.getSecond())+13 : transformCardToInt(p.getSecond());
                maxInd = ind;
            }
            ind++;
        }

        String winner = (maxInd == 0 || maxInd == 2) ? "EW" : "NS";

        starterPosition = maxInd;
        currentPosition = maxInd;

        isNewRound = true;

        return winner;
    }
}
