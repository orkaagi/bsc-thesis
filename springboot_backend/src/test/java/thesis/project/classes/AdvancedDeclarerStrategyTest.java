package thesis.project.classes;

import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdvancedDeclarerStrategyTest {

    // megfelelően állítja-e be a legerősebb színt (összességében leghosszabb szín az adu után), ha van adu
    @Test
    void setStrongestSuitTest1() {
        Map<SuitName, String> testDeclarerHand = new HashMap<>();
        testDeclarerHand.put(SuitName.SPADES, "AKT4");
        testDeclarerHand.put(SuitName.HEARTS, "QJ874");
        testDeclarerHand.put(SuitName.DIAMONDS, "T");
        testDeclarerHand.put(SuitName.CLUBS, "AK53");

        Map<SuitName, String> testDummyHand = new HashMap<>();
        testDummyHand.put(SuitName.SPADES, "QJ875");
        testDummyHand.put(SuitName.HEARTS, "");
        testDummyHand.put(SuitName.DIAMONDS, "QJ854");
        testDummyHand.put(SuitName.CLUBS, "QT97");

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.setStrongestSuit(SuitName.SPADES, testDeclarerHand, testDummyHand);
        assertEquals(SuitName.CLUBS, testStrategy.getStrongestSuit());
    }

    // megfelelően állítja-e be a legerősebb színt (összességében leghosszabb szín), ha nincs adu
    @Test
    void setStrongestSuitTest2() {
        Map<SuitName, String> testDeclarerHand = new HashMap<>();
        testDeclarerHand.put(SuitName.SPADES, "AKT4");
        testDeclarerHand.put(SuitName.HEARTS, "QJ874");
        testDeclarerHand.put(SuitName.DIAMONDS, "T");
        testDeclarerHand.put(SuitName.CLUBS, "AK53");

        Map<SuitName, String> testDummyHand = new HashMap<>();
        testDummyHand.put(SuitName.SPADES, "QJ875");
        testDummyHand.put(SuitName.HEARTS, "");
        testDummyHand.put(SuitName.DIAMONDS, "QJ854");
        testDummyHand.put(SuitName.CLUBS, "QT97");

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.setStrongestSuit(SuitName.NOTRUMP, testDeclarerHand, testDummyHand);
        assertEquals(SuitName.SPADES, testStrategy.getStrongestSuit());
    }

    // megfelelően állítja-e be a felvevő vonalán a meccs kezdetén található aduk számát, ha van adu
    @Test
    void setInitialDeclarerTrumpNumberTest1() {
        Map<SuitName, String> testDeclarerHand = new HashMap<>();
        testDeclarerHand.put(SuitName.SPADES, "AKT4");
        testDeclarerHand.put(SuitName.HEARTS, "QJ8754");
        testDeclarerHand.put(SuitName.DIAMONDS, "");
        testDeclarerHand.put(SuitName.CLUBS, "AK53");

        Map<SuitName, String> testDummyHand = new HashMap<>();
        testDummyHand.put(SuitName.SPADES, "QJ87");
        testDummyHand.put(SuitName.HEARTS, "");
        testDummyHand.put(SuitName.DIAMONDS, "QJ8754");
        testDummyHand.put(SuitName.CLUBS, "T987");

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeDeclarerTrumpNumber(SuitName.SPADES, testDeclarerHand, testDummyHand);
        assertEquals(8, testStrategy.getInitialDeclarerTrumpNumber());
    }

    // meghagyja-e nullának felvevő vonalán a meccs kezdetén található aduk számát, ha nincs adu
    @Test
    void setInitialDeclarerTrumpNumberTest2() {
        Map<SuitName, String> testDeclarerHand = new HashMap<>();
        testDeclarerHand.put(SuitName.SPADES, "AKT4");
        testDeclarerHand.put(SuitName.HEARTS, "QJ8754");
        testDeclarerHand.put(SuitName.DIAMONDS, "");
        testDeclarerHand.put(SuitName.CLUBS, "AK53");

        Map<SuitName, String> testDummyHand = new HashMap<>();
        testDummyHand.put(SuitName.SPADES, "QJ87");
        testDummyHand.put(SuitName.HEARTS, "");
        testDummyHand.put(SuitName.DIAMONDS, "QJ8754");
        testDummyHand.put(SuitName.CLUBS, "T987");

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeDeclarerTrumpNumber(SuitName.NOTRUMP, testDeclarerHand, testDummyHand);
        assertEquals(0, testStrategy.getInitialDeclarerTrumpNumber());
    }

    // a paramétereknek megfelelően állítja-e be asztal kezet, mint adattagot
    @Test
    void initializeDummyHandTest() {
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "AKT");
        testHand.put(SuitName.HEARTS, "QJ8754");
        testHand.put(SuitName.DIAMONDS, "");
        testHand.put(SuitName.CLUBS, "AK53");

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeDummyHand(testHand);
        assertEquals(testHand, testStrategy.getDummyHand());
    }

    // állítja-e be kezdeti üres értékre a megfigyelt kezeket
    @Test
    void initializeObservedSuitsTest() {
        SeatName testSeat1 = SeatName.WEST;
        SeatName testSeat2 = SeatName.EAST;
        Map<SuitName, String> emptyHand = new HashMap<>();
        emptyHand.put(SuitName.SPADES, "");
        emptyHand.put(SuitName.HEARTS, "");
        emptyHand.put(SuitName.DIAMONDS, "");
        emptyHand.put(SuitName.CLUBS, "");

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(testSeat1, testSeat2);
        assertEquals(emptyHand, testStrategy.getObservedSuits().get(SeatName.WEST));
        assertEquals(emptyHand, testStrategy.getObservedSuits().get(SeatName.EAST));
    }

    // első helyen mivel érdemes indulni
    @Test
    void executeTestAtPos1South1(){
        Map<SuitName, String> testDummy = new HashMap<>();
        testDummy.put(SuitName.SPADES, "Q4");
        testDummy.put(SuitName.HEARTS, "K54");
        testDummy.put(SuitName.DIAMONDS, "A654");
        testDummy.put(SuitName.CLUBS, "KJ54");


        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "J32");
        testHand.put(SuitName.HEARTS, "A32");
        testHand.put(SuitName.DIAMONDS, "K32");
        testHand.put(SuitName.CLUBS, "AQ32");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');

        Round testRound = new Round(1, 1, testCards);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.setStrongestSuit(SuitName.NOTRUMP, testHand, testDummy);
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        testStrategy.initializeDummyHand(testDummy);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.SOUTH, SuitName.NOTRUMP, testHand, testRound);
        assertEquals(Pair.of("CLUBS", 'A'), choosenCard);
    }

    // első helyen mivel érdemes indulni
    @Test
    void executeTestAtPos1North1(){
        Map<SuitName, String> testDummy = new HashMap<>();
        testDummy.put(SuitName.SPADES, "J32");
        testDummy.put(SuitName.HEARTS, "A32");
        testDummy.put(SuitName.DIAMONDS, "K32");
        testDummy.put(SuitName.CLUBS, "AQ32");

        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "Q4");
        testHand.put(SuitName.HEARTS, "K54");
        testHand.put(SuitName.DIAMONDS, "A654");
        testHand.put(SuitName.CLUBS, "KJ54");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');

        Round testRound = new Round(3, 3, testCards);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.setStrongestSuit(SuitName.NOTRUMP, testHand, testDummy);
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        testStrategy.initializeDummyHand(testDummy);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.NOTRUMP, testHand, testRound);
        assertEquals(Pair.of("CLUBS", 'K'), choosenCard);
    }

    // első helyen mivel érdemes indulni
    @Test
    void executeTestAtPos1South2(){
        Map<SuitName, String> testDummy = new HashMap<>();
        testDummy.put(SuitName.SPADES, "Q4");
        testDummy.put(SuitName.HEARTS, "K54");
        testDummy.put(SuitName.DIAMONDS, "");
        testDummy.put(SuitName.CLUBS, "K4");


        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "KJ2");
        testHand.put(SuitName.HEARTS, "3");
        testHand.put(SuitName.DIAMONDS, "K32");
        testHand.put(SuitName.CLUBS, "5");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');

        Round testRound = new Round(1, 1, testCards);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.setStrongestSuit(SuitName.NOTRUMP, testHand, testDummy);
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        testStrategy.initializeDummyHand(testDummy);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.SOUTH, SuitName.NOTRUMP, testHand, testRound);
        assertEquals(Pair.of("SPADES", 'K'), choosenCard);
    }

    // első helyen mivel érdemes indulni
    @Test
    void executeTestAtPos1North2(){
        Map<SuitName, String> testDummy = new HashMap<>();
        testDummy.put(SuitName.SPADES, "J32");
        testDummy.put(SuitName.HEARTS, "");
        testDummy.put(SuitName.DIAMONDS, "K32");
        testDummy.put(SuitName.CLUBS, "2");

        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "");
        testHand.put(SuitName.HEARTS, "K5");
        testHand.put(SuitName.DIAMONDS, "AQ64");
        testHand.put(SuitName.CLUBS, "54");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');

        Round testRound = new Round(3, 3, testCards);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.setStrongestSuit(SuitName.NOTRUMP, testHand, testDummy);
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        testStrategy.initializeDummyHand(testDummy);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.NOTRUMP, testHand, testRound);
        assertEquals(Pair.of("DIAMONDS", 'A'), choosenCard);
    }

    // második helyen, ha az induló figurát tett, a játékosnál pedig van az induló színből az indulásnál 1 figura: fed
    @Test
    void executeTestAtPos2HasStarterSuit1(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "Q87");
        testHand.put(SuitName.HEARTS, "K853");
        testHand.put(SuitName.DIAMONDS, "JT52");
        testHand.put(SuitName.CLUBS, "64");

        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "SPADES", 'J', false, SuitName.NOTRUMP);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.SOUTH, SuitName.NOTRUMP, testHand, testRound);
        assertEquals(Pair.of("SPADES", 'Q'), choosenCard);
    }

    // második helyen, ha az induló figurát tett, a játékosnál pedig van az induló színből az indulásnál több nagyobb figura: legkisebbel fed
    @Test
    void executeTestAtPos2HasStarterSuit2(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "T64");
        testHand.put(SuitName.HEARTS, "K83");
        testHand.put(SuitName.DIAMONDS, "J52");
        testHand.put(SuitName.CLUBS, "AKJ7");

        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "CLUBS", 'T', false, SuitName.HEARTS);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.SOUTH, SuitName.HEARTS, testHand, testRound);
        assertEquals(Pair.of("CLUBS", 'J'), choosenCard);
    }

    // második helyen, ha az induló figurát tett, a játékosnál pedig nincs az induló színből az indulásnál nagyobb figura: alacsonyat tesz
    @Test
    void executeTestAtPos2HasStarterSuit3(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "T64");
        testHand.put(SuitName.HEARTS, "T83");
        testHand.put(SuitName.DIAMONDS, "Q52");
        testHand.put(SuitName.CLUBS, "AKJ7");

        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "DIAMONDS", 'K', false, SuitName.HEARTS);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.SOUTH, SuitName.HEARTS, testHand, testRound);
        assertEquals(Pair.of("DIAMONDS", '2'), choosenCard);
    }

    // második helyen, ha az induló nem figurát tett, a játékosnál pedig van az induló színből lap: alacsonyat tesz
    @Test
    void executeTestAtPos2HasStarterSuit4(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "T64");
        testHand.put(SuitName.HEARTS, "T83");
        testHand.put(SuitName.DIAMONDS, "Q52");
        testHand.put(SuitName.CLUBS, "AKJ7");

        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "DIAMONDS", '4', false, SuitName.CLUBS);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.SOUTH, SuitName.CLUBS, testHand, testRound);
        assertEquals(Pair.of("DIAMONDS", '2'), choosenCard);
    }

    // második helyen, ha az induló figurát tett, a játékosnál pedig nincs az induló színből lap, de van nála adu: alacsonyan lop
    @Test
    void executeTestAtPos2HasNoStarterSuitHasTrump1(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "");
        testHand.put(SuitName.HEARTS, "T83");
        testHand.put(SuitName.DIAMONDS, "QT6542");
        testHand.put(SuitName.CLUBS, "A973");

        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "SPADES", 'A', false, SuitName.HEARTS);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.SOUTH, SuitName.HEARTS, testHand, testRound);
        assertEquals(Pair.of("HEARTS", '3'), choosenCard);
    }

    // második helyen, ha az induló nem figurát tett, a játékosnál pedig nincs az induló színből lap, és nincs nála adu: dob legrövidebb színből
    @Test
    void executeTestAtPos2HasNoStarterSuitDispose1(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "");
        testHand.put(SuitName.HEARTS, "T83");
        testHand.put(SuitName.DIAMONDS, "QT6542");
        testHand.put(SuitName.CLUBS, "A973");

        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "SPADES", '5', false, SuitName.HEARTS);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.SOUTH, SuitName.HEARTS, testHand, testRound);
        assertEquals(Pair.of("CLUBS", '3'), choosenCard);
    }

    // második helyen, ha az induló figurát tett, a játékosnál pedig nincs az induló színből lap, és szanzadu van: dob legrövidebb színből
    @Test
    void executeTestAtPos2HasNoStarterSuitDispose2(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "");
        testHand.put(SuitName.HEARTS, "A973");
        testHand.put(SuitName.DIAMONDS, "QT6542");
        testHand.put(SuitName.CLUBS, "632");

        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "SPADES", '7', false, SuitName.NOTRUMP);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.SOUTH, SuitName.NOTRUMP, testHand, testRound);
        assertEquals(Pair.of("CLUBS", '2'), choosenCard);
    }

    // második helyen, ha az induló figurát tett, a játékosnál pedig nincs az induló színből lap, és nincs nála adu: dob legrövidebb színből
    @Test
    void executeTestAtPos2HasNoStarterSuitDispose3(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "");
        testHand.put(SuitName.HEARTS, "T83");
        testHand.put(SuitName.DIAMONDS, "");
        testHand.put(SuitName.CLUBS, "A973");

        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "DIAMONDS", 'K', false, SuitName.SPADES);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.SOUTH, SuitName.SPADES, testHand, testRound);
        assertEquals(Pair.of("HEARTS", '3'), choosenCard);
    }

    // harmadik helyen, ha a játékosnál van az induló színből és eddig a partner viszi kört: kicsit tesz
    @Test
    void executeTestAtPos3HasStarterSuit1(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "Q875");
        testHand.put(SuitName.HEARTS, "K83");
        testHand.put(SuitName.DIAMONDS, "JT52");
        testHand.put(SuitName.CLUBS, "64");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(1,1,testCards);
        testRound.addToCards(SeatName.SOUTH, "SPADES", 'J', false, SuitName.NOTRUMP);
        testRound.addToCards(SeatName.WEST, "SPADES", '3', false, SuitName.NOTRUMP);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.NOTRUMP, testHand, testRound);
        assertEquals(Pair.of("SPADES", '5'), choosenCard);
    }

    // harmadik helyen, ha a játékosnál van az induló színből és eddig az ellenfél viszi a kört, de a játékosnak van annál nagyobb lapja: legalacsonyabb figurával fed
    @Test
    void executeTestAtPos3HasStarterSuit2(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "T64");
        testHand.put(SuitName.HEARTS, "K83");
        testHand.put(SuitName.DIAMONDS, "J52");
        testHand.put(SuitName.CLUBS, "AKQ7");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(1,1,testCards);
        testRound.addToCards(SeatName.SOUTH, "CLUBS", '3', false, SuitName.NOTRUMP);
        testRound.addToCards(SeatName.WEST, "CLUBS", 'J', false, SuitName.NOTRUMP);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.HEARTS, testHand, testRound);
        assertEquals(Pair.of("CLUBS", 'Q'), choosenCard);
    }

    // harmadik helyen, ha a játékosnál van az induló színből és eddig az ellenfél viszi a kört, és a játékosnak nincs nagyobb lapja se aduja szanzadu játékban: kicsit tesz
    @Test
    void executeTestAtPos3HasStarterSuit3(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "T64");
        testHand.put(SuitName.HEARTS, "T83");
        testHand.put(SuitName.DIAMONDS, "Q52");
        testHand.put(SuitName.CLUBS, "AKJ7");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(1,1,testCards);
        testRound.addToCards(SeatName.SOUTH, "DIAMONDS", '3', false, SuitName.NOTRUMP);
        testRound.addToCards(SeatName.WEST, "DIAMONDS", 'K', false, SuitName.NOTRUMP);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.NOTRUMP, testHand, testRound);
        assertEquals(Pair.of("DIAMONDS", '2'), choosenCard);
    }

    // harmadik helyen, ha a játékosnál nincs az induló színből, van aduja, és eddig a partner viszi a kört: legrövidebb nem aduszínből dob
    @Test
    void executeTestAtPos3HasNoStarterSuitHasTrump1(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "");
        testHand.put(SuitName.HEARTS, "83");
        testHand.put(SuitName.DIAMONDS, "T");
        testHand.put(SuitName.CLUBS, "A973");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(1,1,testCards);
        testRound.addToCards(SeatName.SOUTH, "SPADES", 'K', false, SuitName.DIAMONDS);
        testRound.addToCards(SeatName.WEST, "CLUBS", '3', true, SuitName.DIAMONDS);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.DIAMONDS, testHand, testRound);
        assertEquals(Pair.of("HEARTS", '3'), choosenCard);
    }

    // harmadik helyen, ha a játékosnál nincs az induló színből, van aduja, és eddig az ellenfél viszi a kört: alacsonyan lop
    @Test
    void executeTestAtPos3HasNoStarterSuitHasTrump2(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "QT6542");
        testHand.put(SuitName.HEARTS, "T83");
        testHand.put(SuitName.DIAMONDS, "");
        testHand.put(SuitName.CLUBS, "A973");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(1,1,testCards);
        testRound.addToCards(SeatName.SOUTH, "DIAMONDS", '3', false, SuitName.NOTRUMP);
        testRound.addToCards(SeatName.WEST, "DIAMONDS", 'K', false, SuitName.NOTRUMP);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.HEARTS, testHand, testRound);
        assertEquals(Pair.of("HEARTS", '3'), choosenCard);
    }

    // harmadik helyen, ha az induló nem figurát tett, a játékosnál pedig nincs az induló színből lap, és nincs nála adu: dob legrövidebb színből
    @Test
    void executeTestAtPos3HasNoStarterSuitDispose1(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "");
        testHand.put(SuitName.HEARTS, "A9T");
        testHand.put(SuitName.DIAMONDS, "");
        testHand.put(SuitName.CLUBS, "75");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(1,1,testCards);
        testRound.addToCards(SeatName.SOUTH, "DIAMONDS", '9', false, SuitName.SPADES);
        testRound.addToCards(SeatName.WEST, "HEARTS", '6', false, SuitName.SPADES);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.SPADES, testHand, testRound);
        assertEquals(Pair.of("CLUBS", '5'), choosenCard);
    }

    // harmadik helyen, ha az induló figurát tett, a játékosnál pedig nincs az induló színből lap, és szanzadu van: dob legrövidebb színből
    @Test
    void executeTestAtPos3HasNoStarterSuitDispose2(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "");
        testHand.put(SuitName.HEARTS, "8");
        testHand.put(SuitName.DIAMONDS, "QT2");
        testHand.put(SuitName.CLUBS, "632");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(1,1,testCards);
        testRound.addToCards(SeatName.SOUTH, "SPADES", 'J', false, SuitName.NOTRUMP);
        testRound.addToCards(SeatName.WEST, "SPADES", '6', false, SuitName.NOTRUMP);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.NOTRUMP, testHand, testRound);
        assertEquals(Pair.of("HEARTS", '8'), choosenCard);
    }

    // harmadik helyen, ha az induló figurát tett, a játékosnál pedig nincs az induló színből lap, és nincs nála adu: dob legrövidebb színből
    @Test
    void executeTestAtPos3HasNoStarterSuitDispose3(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "73");
        testHand.put(SuitName.HEARTS, "T83");
        testHand.put(SuitName.DIAMONDS, "");
        testHand.put(SuitName.CLUBS, "");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(1,1,testCards);
        testRound.addToCards(SeatName.SOUTH, "DIAMONDS", 'J', false, SuitName.CLUBS);
        testRound.addToCards(SeatName.WEST, "DIAMONDS", '4', false, SuitName.CLUBS);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.CLUBS, testHand, testRound);
        assertEquals(Pair.of("SPADES", '3'), choosenCard);
    }

    // negyedik helyen, ha a játékosnál van az induló színből és eddig a partner viszi kört: kicsit tesz
    @Test
    void executeTestAtPos4HasStarterSuit1(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "Q875");
        testHand.put(SuitName.HEARTS, "K83");
        testHand.put(SuitName.DIAMONDS, "JT52");
        testHand.put(SuitName.CLUBS, "64");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(0,0,testCards);
        testRound.addToCards(SeatName.EAST, "SPADES", 'T', false, SuitName.NOTRUMP);
        testRound.addToCards(SeatName.SOUTH, "SPADES", 'J', false, SuitName.NOTRUMP);
        testRound.addToCards(SeatName.WEST, "SPADES", '3', false, SuitName.NOTRUMP);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.NOTRUMP, testHand, testRound);
        assertEquals(Pair.of("SPADES", '5'), choosenCard);
    }

    // negyedik helyen, ha a játékosnál van az induló színből és eddig az ellenfél viszi a kört, de a játékosnak van annál nagyobb lapja: legalacsonyabb (ha van, figurával) fed
    @Test
    void executeTestAtPos4HasStarterSuit2(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "T64");
        testHand.put(SuitName.HEARTS, "K83");
        testHand.put(SuitName.DIAMONDS, "J52");
        testHand.put(SuitName.CLUBS, "AKQ7");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(0,0,testCards);
        testRound.addToCards(SeatName.EAST, "SPADES", '5', false, SuitName.NOTRUMP);
        testRound.addToCards(SeatName.SOUTH, "CLUBS", '3', true, SuitName.NOTRUMP);
        testRound.addToCards(SeatName.WEST, "SPADES", '9', false, SuitName.NOTRUMP);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.NOTRUMP, testHand, testRound);
        assertEquals(Pair.of("SPADES", 'T'), choosenCard);
    }

    // negyedik helyen, ha a játékosnál van az induló színből és eddig az ellenfél viszi a kört, és a játékosnak nincs nagyobb lapja se aduja szanzadu játékban: kicsit tesz
    @Test
    void executeTestAtPos4HasStarterSuit3(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "T64");
        testHand.put(SuitName.HEARTS, "T83");
        testHand.put(SuitName.DIAMONDS, "Q52");
        testHand.put(SuitName.CLUBS, "AKJ7");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(0,0,testCards);
        testRound.addToCards(SeatName.EAST, "SPADES", '5', false, SuitName.NOTRUMP);
        testRound.addToCards(SeatName.SOUTH, "SPADES", 'J', false, SuitName.NOTRUMP);
        testRound.addToCards(SeatName.WEST, "SPADES", 'K', false, SuitName.NOTRUMP);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.NOTRUMP, testHand, testRound);
        assertEquals(Pair.of("SPADES", '4'), choosenCard);
    }

    // negyedik helyen, ha a játékosnál nincs az induló színből, van aduja, és eddig a partner viszi a kört: legrövidebb nem aduszínből dob
    @Test
    void executeTestAtPos4HasNoStarterSuitHasTrump1(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "");
        testHand.put(SuitName.HEARTS, "83");
        testHand.put(SuitName.DIAMONDS, "T");
        testHand.put(SuitName.CLUBS, "A93");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(0,0,testCards);
        testRound.addToCards(SeatName.EAST, "SPADES", '5', false, SuitName.DIAMONDS);
        testRound.addToCards(SeatName.SOUTH, "SPADES", 'K', false, SuitName.DIAMONDS);
        testRound.addToCards(SeatName.WEST, "CLUBS", '3', true, SuitName.DIAMONDS);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.DIAMONDS, testHand, testRound);
        assertEquals(Pair.of("HEARTS", '3'), choosenCard);
    }

    // negyedik helyen, ha a játékosnál nincs az induló színből, van aduja, és eddig az ellenfél viszi a kört: alacsonyan lop
    @Test
    void executeTestAtPos4HasNoStarterSuitHasTrump2(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "");
        testHand.put(SuitName.HEARTS, "T83");
        testHand.put(SuitName.DIAMONDS, "");
        testHand.put(SuitName.CLUBS, "A93");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(0,0,testCards);
        testRound.addToCards(SeatName.EAST, "SPADES", '5', false, SuitName.HEARTS);
        testRound.addToCards(SeatName.SOUTH, "DIAMONDS", '3', true, SuitName.HEARTS);
        testRound.addToCards(SeatName.WEST, "SPADES", 'K', false, SuitName.HEARTS);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.HEARTS, testHand, testRound);
        assertEquals(Pair.of("HEARTS", '3'), choosenCard);
    }

    // negyedik helyen, ha a játékosnál nincs az induló színből lap, van nála adu és eddig az ellenfél viszi a kört lopással: felüllopja
    @Test
    void executeTestAtPos4HasNoStarterSuitHasTrump3(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "73");
        testHand.put(SuitName.HEARTS, "T83");
        testHand.put(SuitName.DIAMONDS, "");
        testHand.put(SuitName.CLUBS, "");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(0,0,testCards);
        testRound.addToCards(SeatName.EAST, "DIAMONDS", 'K', false, SuitName.HEARTS);
        testRound.addToCards(SeatName.SOUTH, "DIAMONDS", 'T', false, SuitName.HEARTS);
        testRound.addToCards(SeatName.WEST, "HEARTS", '4', true, SuitName.HEARTS);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.HEARTS, testHand, testRound);
        assertEquals(Pair.of("HEARTS", '8'), choosenCard);
    }

    // negyedik helyen, ha nincs az induló színből lap, nincs nála adu és a kört eddig a partner viszi aduval: dob legrövidebb színből
    @Test
    void executeTestAtPos4HasNoStarterSuitDispose1(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "");
        testHand.put(SuitName.HEARTS, "A9T");
        testHand.put(SuitName.DIAMONDS, "");
        testHand.put(SuitName.CLUBS, "75");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(0,0,testCards);
        testRound.addToCards(SeatName.EAST, "SPADES", '5', false, SuitName.DIAMONDS);
        testRound.addToCards(SeatName.SOUTH, "DIAMONDS", '9', true, SuitName.DIAMONDS);
        testRound.addToCards(SeatName.WEST, "SPADES", '6', false, SuitName.DIAMONDS);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.DIAMONDS, testHand, testRound);
        assertEquals(Pair.of("CLUBS", '5'), choosenCard);
    }

    // negyedik helyen, ha nincs az induló színből lap, szanzadu van és eddig az ellenfél viszi a kört: dob legrövidebb színből
    @Test
    void executeTestAtPos4HasNoStarterSuitDispose2(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "");
        testHand.put(SuitName.HEARTS, "8");
        testHand.put(SuitName.DIAMONDS, "QT2");
        testHand.put(SuitName.CLUBS, "");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(0,0,testCards);
        testRound.addToCards(SeatName.EAST, "CLUBS", 'K', false, SuitName.NOTRUMP);
        testRound.addToCards(SeatName.SOUTH, "CLUBS", 'J', false, SuitName.NOTRUMP);
        testRound.addToCards(SeatName.WEST, "DIAMONDS", '6', true, SuitName.NOTRUMP);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.NOTRUMP, testHand, testRound);
        assertEquals(Pair.of("HEARTS", '8'), choosenCard);
    }

    // negyedik helyen, ha a játékosnál nincs az induló színből lap, nincs nála adu és eddig az ellenfél viszi a kört: dob legrövidebb színből
    @Test
    void executeTestAtPos4HasNoStarterSuitDispose3(){
        Map<SuitName, String> testHand = new HashMap<>();
        testHand.put(SuitName.SPADES, "73");
        testHand.put(SuitName.HEARTS, "");
        testHand.put(SuitName.DIAMONDS, "");
        testHand.put(SuitName.CLUBS, "783");

        Pair<String, Character>[] testCards = new Pair[4];
        testCards[0] = Pair.of("", ' ');
        testCards[1] = Pair.of("", ' ');
        testCards[2] = Pair.of("", ' ');
        testCards[3] = Pair.of("", ' ');
        Round testRound = new Round(0,0,testCards);
        testRound.addToCards(SeatName.EAST, "DIAMONDS", 'K', false, SuitName.HEARTS);
        testRound.addToCards(SeatName.SOUTH, "DIAMONDS", 'T', false, SuitName.HEARTS);
        testRound.addToCards(SeatName.WEST, "HEARTS", '4', true, SuitName.HEARTS);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        Pair<String, Character> choosenCard = testStrategy.execute(SeatName.NORTH, SuitName.HEARTS, testHand, testRound);
        assertEquals(Pair.of("SPADES", '3'), choosenCard);
    }
}

