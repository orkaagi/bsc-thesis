package thesis.project.classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserPlayerTest {

    // tetszőleges nem üres színből (pl. pikkből) az első pozíción lévő kártya kijátszása: kártya értéke valóban eltűnik a játékos kezéből, többi szín kárytáinak értéke változatlan
    @Test
    void playCardTestValid1() {
        UserPlayer player = new UserPlayer(SeatName.EAST, "AK7632", "", "T984", "QJT");
        player.playCard(SuitName.SPADES, 0);

        assertEquals("K7632", player.getSuits().get(SuitName.SPADES));
        assertEquals("", player.getSuits().get(SuitName.HEARTS));
        assertEquals("T984", player.getSuits().get(SuitName.DIAMONDS));
        assertEquals("QJT", player.getSuits().get(SuitName.CLUBS));
    }

    // tetszőleges nem üres színből (pl. kőrből) tetszőleges pozíción (pl. harmadikon) lévő kártya kijátszása: kártya értéke valóban eltűnik a játékos kezéből, többi szín kárytáinak értéke változatlan
    @Test
    void playCardTestValid2() {
        UserPlayer player = new UserPlayer(SeatName.WEST, "AK2", "763", "T984", "QJT");
        player.playCard(SuitName.HEARTS, 2);

        assertEquals("AK2", player.getSuits().get(SuitName.SPADES));
        assertEquals("76", player.getSuits().get(SuitName.HEARTS));
        assertEquals("T984", player.getSuits().get(SuitName.DIAMONDS));
        assertEquals("QJT", player.getSuits().get(SuitName.CLUBS));
    }

    // tetszőleges nem üres színből (pl. káróból) tetszőleges pozíción (pl. negyediken) lévő kártya kijátszása: kártya értéke valóban eltűnik a játékos kezéből, többi szín kárytáinak értéke változatlan
    @Test
    void playCardTestValid3() {
        UserPlayer player = new UserPlayer(SeatName.EAST, "K7632", "", "AT984", "QJT");
        player.playCard(SuitName.DIAMONDS, 3);

        assertEquals("K7632", player.getSuits().get(SuitName.SPADES));
        assertEquals("", player.getSuits().get(SuitName.HEARTS));
        assertEquals("AT94", player.getSuits().get(SuitName.DIAMONDS));
        assertEquals("QJT", player.getSuits().get(SuitName.CLUBS));
    }

    // tetszőleges nem üres színből (pl. treffből) utolsó pozíción lévő kártya kijátszása: kártya értéke valóban eltűnik a játékos kezéből, többi szín kárytáinak értéke változatlan
    @Test
    void playCardTestValid4() {
        UserPlayer player = new UserPlayer(SeatName.WEST, "A32", "K76", "T984", "QJT");
        player.playCard(SuitName.CLUBS, 2);

        assertEquals("A32", player.getSuits().get(SuitName.SPADES));
        assertEquals("K76", player.getSuits().get(SuitName.HEARTS));
        assertEquals("T984", player.getSuits().get(SuitName.DIAMONDS));
        assertEquals("QJ", player.getSuits().get(SuitName.CLUBS));
    }

    // üres színből (pl. káróból) próbál meg lapot kijátszani: nem változnak a káryták érétkei
    @Test
    void playCardTestFromVoid1() {
        UserPlayer player = new UserPlayer(SeatName.EAST, "AK7632", "T984", "", "QJT");
        player.playCard(SuitName.DIAMONDS, 1);

        assertEquals("AK7632", player.getSuits().get(SuitName.SPADES));
        assertEquals("T984", player.getSuits().get(SuitName.HEARTS));
        assertEquals("", player.getSuits().get(SuitName.DIAMONDS));
        assertEquals("QJT", player.getSuits().get(SuitName.CLUBS));
    }

    // üres színből (pl. pikkből) próbál meg lapot kijátszani: nem változnak a káryták érétkei
    @Test
    void playCardTestFromVoid2() {
        UserPlayer player = new UserPlayer(SeatName.WEST, "", "AT984", "K632", "QJT7");
        player.playCard(SuitName.SPADES, 1);

        assertEquals("", player.getSuits().get(SuitName.SPADES));
        assertEquals("AT984", player.getSuits().get(SuitName.HEARTS));
        assertEquals("K632", player.getSuits().get(SuitName.DIAMONDS));
        assertEquals("QJT7", player.getSuits().get(SuitName.CLUBS));
    }

    // túl nagy, a sztring hosszánál nagyobb pozíción próbál meg lapot kijátszani (pl. 4. kárytát 1 kárytából): nem változnak a káryták érétkei
    @Test
    void playCardTestInvalidPos1() {
        UserPlayer player = new UserPlayer(SeatName.EAST, "AK7632", "T984", "J", "QT");
        player.playCard(SuitName.DIAMONDS, 3);

        assertEquals("AK7632", player.getSuits().get(SuitName.SPADES));
        assertEquals("T984", player.getSuits().get(SuitName.HEARTS));
        assertEquals("J", player.getSuits().get(SuitName.DIAMONDS));
        assertEquals("QT", player.getSuits().get(SuitName.CLUBS));
    }

    // túl nagy, a sztring hosszánál nagyobb pozíción próbál meg lapot kijátszani (pl. 5. kárytát 3 kárytából): nem változnak a káryták érétkei
    @Test
    void playCardTestInvalidPos2() {
        UserPlayer player = new UserPlayer(SeatName.WEST, "6", "K32", "AT984", "QJT7");
        player.playCard(SuitName.HEARTS, 4);

        assertEquals("6", player.getSuits().get(SuitName.SPADES));
        assertEquals("K32", player.getSuits().get(SuitName.HEARTS));
        assertEquals("AT984", player.getSuits().get(SuitName.DIAMONDS));
        assertEquals("QJT7", player.getSuits().get(SuitName.CLUBS));
    }
}





