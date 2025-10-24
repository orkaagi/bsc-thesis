package thesis.project.classes;

import java.util.*;

public abstract class Player {

    private final SeatName seat;

    private Map<SuitName, String> hand;

    public Player(SeatName seatName, String spades, String hearts, String diamonds, String clubs) {
        this.seat = seatName;
        hand = new HashMap<>();
        hand.put(SuitName.SPADES, spades);
        hand.put(SuitName.HEARTS, hearts);
        hand.put(SuitName.DIAMONDS, diamonds);
        hand.put(SuitName.CLUBS, clubs);
    }

    public SeatName getSeat() { return seat; }

    public Map<SuitName, String> getSuits() {
        return hand;
    }
}
