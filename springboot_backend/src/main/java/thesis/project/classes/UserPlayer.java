package thesis.project.classes;

public class UserPlayer extends Player {

    public UserPlayer(SeatName seatName, String spades, String hearts, String diamonds, String clubs) {
        super(seatName, spades, hearts, diamonds, clubs);
    }

    /**
     * A megadott paraméterek szerint frissíti a játékos kezét: eltávolítja onnan a kijátszott lapot.
     *
     * @param suit      a kijátszott kártya színe
     * @param cardInd   a kijátszott kártya érétk pozíciója a kártyák sztring reprezentációjában
     */
    public void playCard(SuitName suit, int cardInd) {
        if (cardInd < this.getSuits().get(suit).length()) {
            StringBuilder sb = new StringBuilder(this.getSuits().get(suit));
            sb.deleteCharAt(cardInd);
            this.getSuits().replace(suit, sb.toString());
        }
    }
}
