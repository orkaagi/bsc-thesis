package thesis.project.classes;

import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RoundTest {

    // jól alakítja-e át a figurákat, rendes értékeket és üres kártya értékeket
    void transformCardToIntTest() {
        assertEquals(14, Round.transformCardToInt('A'));
        assertEquals(11, Round.transformCardToInt('J'));
        assertEquals(7, Round.transformCardToInt('7'));
        assertEquals(3, Round.transformCardToInt('3'));
        assertEquals(-1, Round.transformCardToInt(' '));
    }

    // valóban üres sztringre és üres karakterre állítja a kártya tömb értékeit, amik nem üresek a teszt elején.
    @Test
    void resetCardsTest() {
        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("DIAMONDS", 'K');
        testCards[2] = Pair.of("HEARTS", '4');
        testCards[3] = Pair.of("", ' ');

        Round round = new Round(1, 3, testCards);
        round.setIsNewRound(false);

        round.resetCards();
        assertEquals(Pair.of("", ' '), round.getCards()[0]);
        assertEquals(Pair.of("", ' '), round.getCards()[1]);
        assertEquals(Pair.of("", ' '), round.getCards()[2]);
        assertEquals(Pair.of("", ' '), round.getCards()[3]);
    }

    // ellenorzes: 1) megfelelo uzenet 2) siker fuggvenyeben megvaltozik-e a round vagy marad 3) első helyes hívás után az isnewround false lesz

    // nem soron következő pozíción ülő játékos próbál lapot kijátszani (pl. 0. helyett a 2. poz.)
    @Test
    void addToCardTestInvalidPlayer1() {
        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');

        Round round = new Round(0, 0, testCards);
        String result = round.addToCards(SeatName.WEST, "SPADES", 'K', false, SuitName.DIAMONDS);
        assertEquals("Invalid player", result);
        assertEquals(testCards, round.getCards());
    }
    // nem soron következő pozíción ülő játékos próbál lapot kijátszani (pl. 1. helyett a 0. poz.)
    @Test
    void addToCardTestInvalidPlayer2() {
        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("DIAMONDS", '7');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');

        Round round = new Round(0, 1, testCards);
        round.setIsNewRound(false);
        String result = round.addToCards(SeatName.EAST, "DIAMONDS", '8', false, SuitName.NOTRUMP);
        assertEquals("Invalid player", result);
        assertEquals(testCards, round.getCards());
    }

    // soron következő pozíción ülő játékos próbál meg más színt kijátszani, mint a kezdőszín, pedig van neki a kezdőszínből (pl. káró helyett kőrt)
    @Test
    void addToCardTestInvalidSuit1() {
        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("DIAMONDS", '7');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');

        Round round = new Round(0, 1, testCards);
        round.setIsNewRound(false);
        String result = round.addToCards(SeatName.SOUTH, "HEARTS", 'Q', false, SuitName.HEARTS);
        assertEquals("Invalid suit", result);
        assertEquals(testCards, round.getCards());
    }

    // soron következő pozíción pozíción ülő játékos próbál meg más színt kijátszani, mint a kezdőszín, pedig van neki a kezdőszínből (pl. pikk helyett kárót)
    @Test
    void addToCardTestInvalidSuit2() {
        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("SPADES", '7');
        testCards[2] = Pair.of("DIAMONDS", '2');
        testCards[3] = Pair.of("", ' ');

        Round round = new Round(1, 3, testCards);
        round.setIsNewRound(false);
        String result = round.addToCards(SeatName.NORTH, "DIAMONDS", '8', false, SuitName.SPADES);
        assertEquals("Invalid suit", result);
        assertEquals(testCards, round.getCards());
    }

    // soron következő pozíción pozíción ülő játékos próbál meg más színt kijátszani, mint a kezdőszín, úgy, hogy a kezdőszínből sikénje van (pl. treff helyett pikket)
    @Test
    void addToCardTestAddCompletedWithVoid1() {
        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("CLUBS", 'A');
        testCards[3] = Pair.of("", ' ');

        Round round = new Round(2, 3, testCards);
        round.setIsNewRound(false);
        String result = round.addToCards(SeatName.NORTH, "SPADES", '8', true, SuitName.SPADES);
        assertEquals("Add completed", result);

        testCards[3] = Pair.of("SPADES", '8');
        assertEquals(testCards, round.getCards());
    }

    // soron következő pozíción pozíción ülő játékos próbál meg más színt kijátszani, mint a kezdőszín, úgy, hogy a kezdőszínből sikénje van (pl. káró helyett treffet)
    @Test
    void addToCardTestAddCompletedWithVoid2() {
        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("DIAMONDS", 'T');
        testCards[1] = Pair.of("DIAMONDS", 'K');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');

        Round round = new Round(0, 2, testCards);
        round.setIsNewRound(false);
        String result = round.addToCards(SeatName.WEST, "CLUBS", '2', true, SuitName.NOTRUMP);
        assertEquals("Add completed", result);

        testCards[3] = Pair.of("CLUBS", '2');
        assertEquals(testCards, round.getCards());
    }

    // játékos kezdőpozíción játszik ki valamit (pl. kőrt)
    @Test
    void addToCardTestAddCompletedNewRound1() {
        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');

        Round round = new Round(0, 0, testCards);
        String result = round.addToCards(SeatName.EAST, "HEARTS", 'T', true, SuitName.CLUBS);
        assertEquals("Add completed", result);
        assertFalse(round.getIsNewRound());

        testCards[0] = Pair.of("HEARTS", 'T');
        assertEquals(testCards, round.getCards());
    }

    // játékos kezdőpozíción játszik ki valamit (pl. pikket)
    @Test
    void addToCardTestAddCompletedNewRound2() {
        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');

        Round round = new Round(3, 3, testCards);
        round.setIsNewRound(false);
        String result = round.addToCards(SeatName.NORTH, "SPADES", 'A', true, SuitName.SPADES);
        assertEquals("Add completed", result);
        assertFalse(round.getIsNewRound());

        testCards[3] = Pair.of("SPADES", 'A');
        assertEquals(testCards, round.getCards());
    }

    // soron következő pozíción pozíción ülő játékos próbál meg a kezdőszínből kijátszani (pl. treff)
    @Test
    void addToCardTestAddCompleted1() {
        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("CLUBS", 'T');
        testCards[2] = Pair.of("CLUBS", 'A');
        testCards[3] = Pair.of("", ' ');

        Round round = new Round(2, 3, testCards);
        round.setIsNewRound(false);
        String result = round.addToCards(SeatName.NORTH, "CLUBS", '4', false, SuitName.CLUBS);
        assertEquals("Add completed", result);

        testCards[3] = Pair.of("CLUBS", '4');
        assertEquals(testCards, round.getCards());
    }

    // soron következő pozíción pozíción ülő játékos próbál meg a kezdőszínből kijátszani (pl. káró)
    @Test
    void addToCardTestAddCompleted2() {
        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("DIAMONDS", 'T');
        testCards[1] = Pair.of("HEARTS", '3');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');

        Round round = new Round(0, 2, testCards);
        round.setIsNewRound(false);
        String result = round.addToCards(SeatName.WEST, "DIAMONDS", '6', true, SuitName.HEARTS);
        assertEquals("Add completed", result);

        testCards[3] = Pair.of("DIAMONDS", '6');
        assertEquals(testCards, round.getCards());
    }

    // helyesen adja-e meg a legnagyobb lapot kijátszó játékost, ha még nem ért véget a kört és nincs aduszín
    @Test
    void getRoundWinnerTest1() {
        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("DIAMONDS", 'T');
        testCards[1] = Pair.of("HEARTS", 'A');
        testCards[2] = Pair.of("DIAMONDS", '8');
        testCards[3] = Pair.of("", ' ');

        Round round = new Round(0, 3, testCards);
        round.setIsNewRound(false);


        Pair<SeatName, Integer> winner = round.getRoundWinner(SuitName.NOTRUMP);
        assertEquals(Pair.of(SeatName.EAST, 10), winner);
    }

    // helyesen adja-e meg a legnagyobb lapot kijátszó játékost, ha már véget ért a kört és van aduszín
    @Test
    void getRoundWinnerTest2() {
        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("SPADES", 'T');
        testCards[1] = Pair.of("SPADES", 'J');
        testCards[2] = Pair.of("HEARTS", '3');
        testCards[3] = Pair.of("HEARTS", '5');

        Round round = new Round(3, 3, testCards);

        Pair<SeatName, Integer> winner = round.getRoundWinner(SuitName.HEARTS);
        assertEquals(Pair.of(SeatName.NORTH, 18), winner);
    }

    // helyesen adja-e meg a legnagyobb lapot kijátszó játékost, ha több játékos is van, aki még nem játszott ki a körben lapot
    @Test
    void getRoundWinnerTest3() {
        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("SPADES", 'J');
        testCards[2] = Pair.of("HEARTS", '3');
        testCards[3] = Pair.of("", ' ');

        Round round = new Round(1, 3, testCards);

        Pair<SeatName, Integer> winner = round.getRoundWinner(SuitName.SPADES);
        assertEquals(Pair.of(SeatName.SOUTH, 24), winner);
    }
}





