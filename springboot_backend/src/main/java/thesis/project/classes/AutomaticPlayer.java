package thesis.project.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.util.Pair;

public class AutomaticPlayer extends Player {

    private Strategy strategy;

    public AutomaticPlayer(SeatName seatName, String spades, String hearts, String diamonds, String clubs, Strategy strategy) {
        super(seatName, spades, hearts, diamonds, clubs);
        this.strategy = strategy;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    @JsonIgnore
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Frissíti a játékos kezét, úgy, hogy eltávolítja onnan a startégia szerint (a <code>strategy.execute</code> metódus által) kiválasztott lapot.
     *
     * @param trump a játékban érvényes adu(szín)
     * @param round az éppek aktuális kör
     * @return      the card to be played represented by its suit name as a string and its value as a character
     */
    public Pair<String, Character> playCard(SuitName trump, Round round) {

        Pair<String, Character> cardToBePlayed = strategy.execute(this.getSeat(), trump, this.getSuits(), round);
        SuitName suitOfChosenCard = SuitName.valueOf(cardToBePlayed.getFirst());
        char valueOfChosenCard = cardToBePlayed.getSecond();

        System.out.println("Automatic - " + suitOfChosenCard + " before: " + this.getSuits().get(suitOfChosenCard));

        StringBuilder sb = new StringBuilder(this.getSuits().get(suitOfChosenCard));
        int index = sb.indexOf(String.valueOf(valueOfChosenCard));
        sb.deleteCharAt(index);
        // itt -1-es indexet keres
        this.getSuits().replace(suitOfChosenCard, String.valueOf(sb));

        System.out.println("Automatic - " + suitOfChosenCard + " after: " + this.getSuits().get(suitOfChosenCard));

        return cardToBePlayed;
    }
}
