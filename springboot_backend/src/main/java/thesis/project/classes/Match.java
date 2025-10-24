package thesis.project.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Match {

    private int score_NS = 0;

    private int score_EW = 0;

    private String contract = "";

    private Map<SeatName, Player> players = new HashMap<>();

    private Round round = new Round();

    private int gameId = -1;

    private boolean isGameOver = false;

    public Match() {}

    public Match(int score_NS, int score_EW, String contract, int gameId) {
        this.score_NS = score_NS;
        this.score_EW = score_EW;
        this.contract = contract;
        this.gameId = gameId;
    }

    public int getScore_NS() {
        return score_NS;
    }

    public void setScore_NS(int score_NS) {
        this.score_NS = score_NS;
    }

    public int getScore_EW() {
        return score_EW;
    }

    public void setScore_EW(int score_EW) {
        this.score_EW = score_EW;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public Map<SeatName, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<SeatName, Player> players) {
        this.players = players;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @JsonIgnore
    public int getBid() {
        System.out.println("Magasság: " + contract.charAt(0));
        System.out.println("Válallt ütések: " + (Character.getNumericValue(contract.charAt(0))+6));
        return Character.getNumericValue(contract.charAt(0))+6;
    }

    public void resetRound() {
        round = new Round();
    }

    public Player getPlayerBySeat(SeatName seat){
        return players.get(seat);
    }

    public void updatePlayers(SeatName seat, Player player){
        this.players.put(seat, player);
    }

    /**
     * Checks if the provided player can play the provided card in the current round by calling <code>isPlayValidForCurrentRound</code>.
     * If the call is valid, propagates the card playing logic to the player object by calling its <code>playCard</code> method.
     * Afterward manages the effect of the round by calling <code>manageEffectOfCall</code>.
     *
     * @param seat  seat name of the player who wants to play a card
     * @param suitname  suit name of the suit from which the player wants to play a card
     * @param card  value of the card which the player wants to play
     * @param ind   the position of the card which the player wants to play within its suit
     */
    public void playUserCard(SeatName seat, String suitname, char card, int ind) {
        String state = isPlayValidForCurrentRound(seat, suitname, card);
        if(!Objects.equals(state, "Invalid")){
            UserPlayer player = (UserPlayer) players.get(seat);
            player.playCard(SuitName.valueOf(suitname), ind);
            manageEffectOfCall(state);

            if (getPlayerBySeat(SeatName.NORTH) instanceof AutomaticPlayer northPlayer) {
                northPlayer.getStrategy().updateObservedSuits(seat, SuitName.valueOf(suitname), card);
            }
            if (getPlayerBySeat(SeatName.SOUTH) instanceof AutomaticPlayer southPlayer) {
                southPlayer.getStrategy().updateObservedSuits(seat, SuitName.valueOf(suitname), card);
            }
        }
    }


    /**
     * Determines which card to play and propagates the card playing logic to the player object by calling its <code>playCard</code> method.     *
     * Updates the cards in the current round by calling <code>isPlayValidForCurrentRound</code>.
     * Afterward manages the effect of the round by calling <code>manageEffectOfCall</code>.
     *
     * @param seat  seat name of the player who wants to play a card
     */
    public void playAutomaticCard(SeatName seat) {
        AutomaticPlayer player = (AutomaticPlayer) players.get(seat);
        if(round.getPositions().get(seat) == round.getCurrentPosition()){
            Pair<String, Character> cardToPlay = player.playCard(getTrump(), this.round);
            String suit = cardToPlay.getFirst();
            char card = cardToPlay.getSecond();
            String state = isPlayValidForCurrentRound(seat, suit, card);
            if (!Objects.equals(state, "Invalid")) {
                manageEffectOfCall(state);

                if (seat == SeatName.WEST && getPlayerBySeat(SeatName.NORTH) instanceof AutomaticPlayer northPlayer) {
                    northPlayer.getStrategy().updateObservedSuits(seat, SuitName.valueOf(suit), card);
                }
                if (seat == SeatName.WEST && getPlayerBySeat(SeatName.SOUTH) instanceof AutomaticPlayer southPlayer) {
                    southPlayer.getStrategy().updateObservedSuits(seat, SuitName.valueOf(suit), card);
                }
            }
        }
    }

    @JsonIgnore
    public SuitName getTrump() {
        System.out.println("Adu: " + contract.charAt(1));
        return switch (contract.charAt(1)) {
            case 'S' -> SuitName.SPADES;
            case 'H' -> SuitName.HEARTS;
            case 'D' -> SuitName.DIAMONDS;
            case 'C' -> SuitName.CLUBS;
            case 'N' -> SuitName.NOTRUMP;
            default -> null;
        };
    }

    private String isPlayValidForCurrentRound(SeatName seat, String suit, char card) {
        String state = "";
        if(round.getIsNewRound()){
            round.resetCards();

            state = round.addToCards(seat, suit, card, false, getTrump());

            if(Objects.equals(state, "Invalid player")) {
                //System.out.println("No action: " + state + " (currentIndex = " + round.getCurrentIndex() + ", currentSuit = " + round.getRound()[round.getStarterIndex()].getFirst() + ")");
                return "Invalid";
            }
        }
        else {
            String starterSuit = round.getCards()[round.getStarterPosition()].getFirst();
            state = round.addToCards(seat, suit, card, checkVoid(seat, SuitName.valueOf(starterSuit)), getTrump());

            if(Objects.equals(state, "Invalid suit") || Objects.equals(state, "Invalid player")) {
                //System.out.println("No action: " + state + " (currentIndex = " + round.getCurrentIndex() + ", currentSuit = " + round.getRound()[round.getStarterIndex()].getFirst() + ")");
                return "Invalid";
            }
        }
        return state;
    }

    private boolean checkVoid(SeatName seat, SuitName suit){
        if(Objects.equals(seat, SeatName.NORTH)
                || Objects.equals(seat, SeatName.SOUTH)
                || Objects.equals(seat, SeatName.EAST)
                || Objects.equals(seat, SeatName.WEST)) {
            return getPlayerBySeat(seat).getSuits().get(suit).isEmpty();
        } else {
            throw new RuntimeException("Invalid seat name!");
        }
    }

    private void manageEffectOfCall(String state) {
        if(round.getIsNewRound())
        {
            //System.out.println("ROUND ENDED with the following result: " + state + ", starterIndex = " + round.getStarterIndex());

            if(Objects.equals(state, "NS")) {
                score_NS++;
            } else if (Objects.equals(state, "EW")) {
                score_EW++;
            }

            // DEMO:
            if ((score_NS + score_EW) == 13) {
                setGameOver(true);
            }
        }
    }
}
