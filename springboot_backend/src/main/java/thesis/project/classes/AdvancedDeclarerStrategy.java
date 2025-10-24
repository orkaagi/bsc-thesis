package thesis.project.classes;

import org.springframework.data.util.Pair;

import java.util.*;

public class AdvancedDeclarerStrategy extends Strategy {

    public AdvancedDeclarerStrategy() {
        super();
    }

    /**
     * Updates observedSuit attribute by adding the cards from the provided round to the observations.
     *
     * @param   seat seat of the player who observes suits
     * @param   suit current round of cards
     * @param   card kártya
     */
    public void updateObservedSuits(SeatName seat, SuitName suit, char card) {

        System.out.println(seat.name() + " - observed suits before update: " + getObservedSuits().get(seat).get(suit));

        Map<SuitName, String> observedHand = getObservedSuits().get(seat);
        observedHand.replace(suit, (observedHand.get(suit) + card));
        getObservedSuits().replace(seat, observedHand);

        System.out.println(seat.name() + "- observed suits after update: " + getObservedSuits().get(seat).get(suit));

    }

    protected Pair<String, Character> selectCardPos1(Map<SuitName, String> hand, SuitName trump, SeatName seat) {

        SuitName bestSuit = chooseSuitForLead(trump, hand);

        String dummyHand = getDummyHand().get(bestSuit);
        String cardsOfBestSuit = hand.get(bestSuit);

        // 1
        Pair<String, String> originalOpponentHands = generateOpponentHands(bestSuit, hand, getObservedSuits());

        // 2
        List<Character>[] allHands = encodeAllHands(cardsOfBestSuit,
                dummyHand,
                originalOpponentHands.getFirst(),
                originalOpponentHands.getSecond());
        char[] currentRound = {'y', 'y', 'y', 'y'};
        boolean isNoTrump = (trump == SuitName.NOTRUMP);
        Node base = new Node(null, isNoTrump, 0, allHands, currentRound);
        base.setChild(base.createChildren());

        //3
        // itt igazából inkább a base összes gyerekére kéne postorder?
        postorder(base);

        // 4
        double maxScore = 0.0;
        char bestLead = 'y';
        Node child = base.getChild();

        while (child != null) {
            System.out.println(child.getScore());
            System.out.println(child.getSelectedLead());
            if(child.getSelectedLead() != 'v' && child.getScore() > maxScore){
                maxScore = child.getScore();
                bestLead = child.getSelectedLead();
            }
            child = child.getSibling();
        }

        System.out.println("Automatic selected lead: " + bestSuit.name() + " " + bestLead + ", expected maximum score: " + maxScore);

        if (bestLead == 'x' || bestLead == 'y') {
            bestLead = hand.get(bestSuit).charAt(cardsOfBestSuit.length()-1);
            System.out.println("specific best lead: " + bestLead);
        }

        return Pair.of(bestSuit.name(), bestLead); // can be y, if Suit is xxxx, maxScore = 0.0
    }

    private SuitName chooseSuitForLead(SuitName trump, Map<SuitName, String> hand) {
        if (trump != SuitName.NOTRUMP && !hand.get(trump).isEmpty()){
            int opponentTrumpNumber = getObservedSuits().get(SeatName.EAST).get(trump).length() + getObservedSuits().get(SeatName.WEST).get(trump).length();
            if (opponentTrumpNumber < (13 - getInitialDeclarerTrumpNumber())) {
                return trump;
            }
        }
        System.out.println(getStrongestSuit());
        if (!hand.get(getStrongestSuit()).isEmpty()) {
            return getStrongestSuit();
        }
        return findLongestSuit(hand);
    }

    /**
     * Traverse an ordinary tree in a postorder manner.
     *
     * @param   node root node of the tree to be traversed.
     */
    private void postorder(Node node) {
        while (node != null) {
            postorder(node.getChild());
            node.process(node);
            node = node.getSibling();
        }
    }

    /**
     * Estimates the cards of the opponents in a specified suit. First determines the remainder cards of that suit by calling <code>determineRemainderCards</code>, then distributes it among the two opponents.
     *
     * @param suit              target suit for estimation
     * @param observedSuits     information of the cards which are already out play or in declarer's and dummy's hand
     * @return                  the cards of the opponents in the specified suit as a string
     */
    private Pair<String, String> generateOpponentHands(SuitName suit, Map<SuitName, String> hand, Map<SeatName, Map<SuitName, String>> observedSuits) {

        ArrayList<Character> remainderCards = determineRemainderCards(suit, hand, observedSuits);

        StringBuilder opponentHand1 = new StringBuilder();
        StringBuilder opponentHand2 = new StringBuilder();

        boolean parity = true;
        for(char c : remainderCards) {
            if(parity){
                opponentHand1.append(c);
                parity = false;
            } else {
                opponentHand2.append(c);
                parity = true;
            }
        }

        System.out.println("Oppent hands created successfully: " + opponentHand1 + ", " + opponentHand2);
        return Pair.of(String.valueOf(opponentHand1), String.valueOf(opponentHand2));
    }

    private ArrayList<Character> determineRemainderCards(SuitName suit, Map<SuitName, String> hand, Map<SeatName, Map<SuitName, String>> observedSuits) {
        List<String> hands = new ArrayList<>(List.of(
                hand.get(suit),
                getDummyHand().get(suit),
                observedSuits.get(SeatName.EAST).get(suit),
                observedSuits.get(SeatName.WEST).get(suit)));

        ArrayList<Character> remainderCards = new ArrayList<>(List.of('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'));
        for(String h : hands) {
            for(char c : h.toCharArray()){
                int ind = remainderCards.indexOf(c);
                if(ind!=-1){
                    remainderCards.remove(ind);
                }
            }
        }

        return remainderCards;
    }

    /**
     * Decodes a suit from a string to a list of characters, where characters between '2' and '9' are encoded to 'x'.
     *
     * @param   northCards north's cards of the specified suit to be encoded. E.g. "5"
     * @param   southCards south's cards of the specified suit to be encoded. E.g. "AQ6"
     * @param   eastCards east's cards of the specified suit to be encoded. E.g. "T8"
     * @param   westCards west's cards of the specified suit to be encoded. E.g. ""
     * @return  all cards of a suit for each player in an encoded format. E.g. {{x,v,v}, {A,Q,x}, {T,x,v}, {v,v,v}}
     */
    private List<Character>[] encodeAllHands(String northCards, String southCards, String eastCards, String westCards) {

        List<String> allHands = new ArrayList<>(List.of(northCards, southCards, eastCards, westCards));

        int lengthOfLongestDeclarerSuit = Math.max(northCards.length(), westCards.length());

        List<List<Character>> encodedHands = new ArrayList<>();

        for(String hand : allHands){
            List<Character> encodedHand = new ArrayList<>();
            int len = Math.min(lengthOfLongestDeclarerSuit, hand.length());
            int ind = 0;
            while (ind < len){
                char s = hand.charAt(ind);
                if(s == 'A' || s == 'K' || s == 'Q' || s == 'J' || s == 'T') {
                    encodedHand.add(s);
                } else {
                    encodedHand.add('x');
                }
                ind++;
            }
            while (ind < lengthOfLongestDeclarerSuit){
                encodedHand.add('v');
                ind++;
            }
            encodedHands.add(encodedHand);
        }

        return new List[]{encodedHands.get(0), encodedHands.get(1), encodedHands.get(2), encodedHands.get(3)};
    }
}
