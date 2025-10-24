package thesis.project.classes;

import org.springframework.data.util.Pair;
import thesis.project.exception.CustomInvalidArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

    private Node child = null;

    private Node sibling = null;

    private Node parent;

    private final boolean isNoTrump;

    private double score;

    private double maxScoreSiblings = 0;

    /**
     * A tömb elemek rendre észak, dél, kelet és nyugat kezei a kiválasztott színből. Mindegyik kezet egy karakter lista reprezentál.
     */
    private List<Character>[] hands;

    /**
     * A <code>Node</code>-hoz tartozó állás kódolt kártyákkal, vagyis a játékosok által az adott körben kijátszott lapok.
     */
    private char[] round;

    public Node(Node parent, boolean isNoTrump, double score, List<Character>[] hands, char[] round) {
        this.parent = parent;
        this.isNoTrump = isNoTrump;
        this.score = score;
        this.hands = hands;
        this.round = round;
    }

    public Node getChild() {
        return child;
    }

    public void setChild(Node child) {
        this.child = child;
    }

    public Node getSibling() {
        return sibling;
    }

    public void setSibling(Node sibling) {
        this.sibling = sibling;
    }

    public Node getParent() {
        return parent;
    }

    public boolean getIsNoTrump() {
        return this.isNoTrump;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getMaxScoreSiblings() {
        return maxScoreSiblings;
    }

    public void setMaxScoreSiblings(double maxScoreSiblings) {
        this.maxScoreSiblings = maxScoreSiblings;
    }

    public char[] getRound() {
        return round;
    }

    public void setRound(char[] round) {
        this.round = round;
    }

    public List<Character>[] getHands() {
        return hands;
    }

    public void setHands(List<Character>[] hands) {
        this.hands = hands;
    }

    /**
     * Visszaadja az állás (<code>round</code> adattag) alapján a felvevő által kijátszott lapot.     *
     * @return  a játékos hívása (x-ként kódolva, ha a kártya értéke 2 és 9 közé esik)
     */
    public char getSelectedLead() {
        return round[0];
    }

    /**
     * Determines the expected maximum of achievable score for <code>node</code> after a specific lead.
     *
     * @param node  a Node with initialized <code>round</code> member.
     */
    public void process(Node node) {
        // play cards
        node.playCardInNode(0, node.getRound()[0]);
        node.playCardInNode(1, node.getRound()[1]);
        node.playCardInNode(2, node.getRound()[2]);
        node.playCardInNode(3, node.getRound()[3]);

        // calculate score
        double newScore = node.getScore() + node.scoreOfRound();
        node.setScore(newScore);

        if (node.getMaxScoreSiblings() < node.getScore()) {
            node.setMaxScoreSiblings(node.getScore());
        }

        if (node.getSibling() != null){
            node.getSibling().setMaxScoreSiblings(node.getMaxScoreSiblings());
        } else if (node.getParent() != null && node.getParent().getScore() < node.getMaxScoreSiblings()) {
            node.getParent().setScore(node.getMaxScoreSiblings());
        }

        // tobbi kezre is kellene ellenorzes? oket kiegesziteni valamivel, ha nekik elfogy a szin
        if(!node.hands[0].isEmpty() && !node.hands[1].isEmpty()) {
            node.setChild(createChildren());
        }
    }

    /**
     * Generates the children of a Node, considering all possible rounds and assigning a round to each child. The possible rounds are defined by calling <code>generatePossibleRounds()</code>.
     *
     * @return  The first child of <code>this</code>.
     */
    // given a node and its round creates its children (siblings, whose rounds are successors of the initial round)
    public Node createChildren() {
        ArrayList<Node> children = new ArrayList<>();

        List<char[]> possibleRounds = generatePossibleRounds(); // <[AKxx], [KJxx], [xxxx], ....>

        for(char[] round : possibleRounds){
            Node newNode = new Node(this, this.isNoTrump, this.score, this.hands, round);
            children.add(newNode);
        }

        for(int i = 0; i < children.size()-1; i++){
            children.get(i).setSibling(children.get(i+1));
        }

        if(!children.isEmpty())
            return children.get(0);

        return null;
    }

    private void playCardInNode(int playerInd, char card) {
        int ind = hands[playerInd].indexOf(card);
        if(ind!=-1){
            hands[playerInd].remove(ind);
        }
    }

    private int transformCardToIntInNode(char card, boolean isNoTrump) {
        if (isNoTrump){
            return switch (card) {
                case 'A' -> 5;
                case 'K' -> 4;
                case 'Q' -> 3;
                case 'J' -> 2;
                case 'T' -> 1;
                case 'v' -> -1;
                default -> 0;
            };
        }
        return switch (card) {
            case 'A' -> 5;
            case 'K' -> 4;
            case 'Q' -> 3;
            case 'J' -> 2;
            case 'T' -> 1;
            default -> 0;
        };
    }

    /**
     * "Fitness function" for a given round. Helps determine whether this choice of cards leads to North's or South's win.
     *
     * @return  1 if North or South wins, 0 if it can't be determined from the coded form, e.g. [x,x,x,x] or [x,T,x,T], -1 otherwise.
     */
    private double scoreOfRound() {
        int northValue = transformCardToIntInNode(round[0], isNoTrump);
        int southValue = transformCardToIntInNode(round[1], isNoTrump);
        int eastValue = transformCardToIntInNode(round[2], isNoTrump);
        int westValue = transformCardToIntInNode(round[3], isNoTrump);

        if((northValue > eastValue && northValue > westValue) || (southValue > eastValue && southValue > westValue)) {
            return 1;
        }
        if((northValue == westValue && northValue == eastValue) || (southValue == westValue && southValue == eastValue)) {
            return 0;
        }
        return -1;
    }

    /**
     * Creates a list of possible leads knowing the hands of the players. Each element of a list is an array of length two.
     *
     * @param partnership abbreviation for seats of the partnership: "NS" or "EW"
     *
     * @return  The possible leads of a partnership in the following format: <code>List<[lead of N, lead of S]></code>.
     */
    // encoding of cards to ints: A-4, K-3, Q-2, J-1, T..2-0    !!!
    // finomitasi opcio: T=0, 9..2=-1
    private List<Pair<Character, Character>> listDifferentLeadsOfPartnership(String partnership) {
        int playerInd;
        if(Objects.equals(partnership, "NS")) {
            playerInd = 0;
        } else if(Objects.equals(partnership, "EW")) {
            playerInd = 2;
        } else {
            throw new CustomInvalidArgumentException("The argument partnership must be either \"NS\" or \"EW\"");
        }

        List<Pair<Character, Character>> leadList = new ArrayList<>();

        int NumOfCards1 = hands[playerInd].size();
        int NumOfCards2 = hands[playerInd+1].size();

        for(int i = 0; i < NumOfCards1; i++){
            for(int j = 0; j < NumOfCards2; j++){
                char card1 = hands[playerInd].get(i);
                char card2 = hands[playerInd+1].get(j);
                leadList.add(Pair.of(card1, card2));

                // avoid multiple x-x pairs
                if(card1 == 'x' && card2 == 'x') {
                    // avoid writing "break"
                    i = NumOfCards1;
                    j = NumOfCards2;
                }
            }
        }

        return leadList;
    }

    /**
     * Generates possible rounds considering all different leads/choice of cards from both partnerships. All different leads per partnerships are defined by calling <code>listDifferentLeadsPerPartners()</code>.
     *
     * @return  all possible rounds in a list, e.g. <code><[AKxx], [KJxx], [xxxx], ....></code>
     */
    private List<char[]> generatePossibleRounds() {
        List<char[]> newRounds = new ArrayList<>();

        List<Pair<Character, Character>> NSList = listDifferentLeadsOfPartnership("NS");
        List<Pair<Character, Character>> EWList = listDifferentLeadsOfPartnership("EW");

        for(Pair<Character, Character> nsleads : NSList) {
            for(Pair<Character, Character> ewleads : EWList) {
                char[] nsewleads = {nsleads.getFirst(), nsleads.getSecond(), ewleads.getFirst(), ewleads.getSecond()};
                newRounds.add(nsewleads);
            }
        }

        return newRounds;
    }
}
