package thesis.project.classes;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NodeTest {

    // ellenőrzi, hogy jó-e a selectedLead a round alapján
    @Test
    void getSelectedLeadTest() {
        List<Character>[] hands = new List[] {List.of('K', 'T'), List.of('Q', '9', '8'), List.of('A', '5', '2'), List.of('7', '3')};
        char[] round = {'K', '8', '2', '3'};
        Node node = new Node(null, false, 0, hands, round);

        assertEquals('K', node.getSelectedLead());
    }

    // ellenőrzi, hogy az első gyereket megfelelően hozta-e létre (adattagok helyesek-e)
    @Test
    void createChildrenTestFirstChild() {
        List<Character>[] hands = new List[] {List.of('K', 'T'), List.of('Q', '9', '8'), List.of('A', '6'), List.of('7', '3')};
        char[] round = {'J', '4', '2', '5'};
        Node node = new Node(null, false,2, hands, round);

        Node child = node.createChildren();
        assertThat(child).isNotNull();
        assertThat(child.getChild()).isNull();
        assertThat(child.getSibling()).isNotNull();
        assertEquals(2, child.getScore());
        assertEquals(0, child.getMaxScoreSiblings());
        assertEquals(hands, child.getHands());
    }

    // ellenőrzi, hogy az összes gyereket megfelelően hozta-e létre (adattagjaik helyesek-e)
    @Test
    void createChildrenTestEveryChild() {
        List<Character>[] hands = new List[] {List.of('K', 'T'), List.of('Q', '9', '8'), List.of('A', '6'), List.of('7', '3')};
        char[] round = {'J', '4', '2', '5'};
        Node node = new Node(null, false, 2, hands, round);

        Node child = node.createChildren();

        while (child.getSibling() != null) {
            assertThat(child).isNotNull();
            assertThat(child.getChild()).isNull();
            assertEquals(2, child.getScore());
            assertEquals(0, child.getMaxScoreSiblings());
            child = child.getSibling();
        }
    }
}





