package thesis.project.classes;

import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutomaticPlayerTest {

    // második helyen, ha az induló figurát tett, a játékosnál pedig van az induló színből az indulásnál 1 figura: fed
    @Test
    void playCardTestSDSAtPos2HasStarterSuit1(){
        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "SPADES", 'J', false, SuitName.NOTRUMP);

        SimpleDeclarerStrategy testStrategy = new SimpleDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        AutomaticPlayer testPlayer = new AutomaticPlayer(SeatName.SOUTH, "Q87", "K853", "JT52", "64", testStrategy);

        Pair<String, Character> choosenCard = testPlayer.playCard(SuitName.NOTRUMP, testRound);
        assertEquals(Pair.of("SPADES", 'Q'), choosenCard);
    }

    @Test
    void playCardTestADSAtPos2HasStarterSuit1(){
        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "SPADES", 'J', false, SuitName.NOTRUMP);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        AutomaticPlayer testPlayer = new AutomaticPlayer(SeatName.SOUTH, "Q87", "K853", "JT52", "64", testStrategy);

        Pair<String, Character> choosenCard = testPlayer.playCard(SuitName.NOTRUMP, testRound);
        assertEquals(Pair.of("SPADES", 'Q'), choosenCard);
    }

    @Test
    void playCardTestOSAtPos2HasStarterSuit1(){
        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "SPADES", 'J', false, SuitName.NOTRUMP);

        OpponentStrategy testStrategy = new OpponentStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        AutomaticPlayer testPlayer = new AutomaticPlayer(SeatName.SOUTH, "Q87", "K853", "JT52", "64", testStrategy);

        Pair<String, Character> choosenCard = testPlayer.playCard(SuitName.NOTRUMP, testRound);
        assertEquals(Pair.of("SPADES", 'Q'), choosenCard);
    }

    // második helyen, ha az induló figurát tett, a játékosnál pedig nincs az induló színből lap, de van nála adu: alacsonyan lop
    @Test
    void playCardTestSDSAtPos2HasNoStarterSuitHasTrump1(){
        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "SPADES", 'A', false, SuitName.HEARTS);

        SimpleDeclarerStrategy testStrategy = new SimpleDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        AutomaticPlayer testPlayer = new AutomaticPlayer(SeatName.SOUTH, "", "T83", "QT6542", "A973", testStrategy);

        Pair<String, Character> choosenCard = testPlayer.playCard(SuitName.HEARTS, testRound);
        assertEquals(Pair.of("HEARTS", '3'), choosenCard);
    }

    @Test
    void playCardTestADSAtPos2HasNoStarterSuitHasTrump1(){
        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "SPADES", 'A', false, SuitName.HEARTS);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        AutomaticPlayer testPlayer = new AutomaticPlayer(SeatName.SOUTH, "", "T83", "QT6542", "A973", testStrategy);

        Pair<String, Character> choosenCard = testPlayer.playCard(SuitName.HEARTS, testRound);
        assertEquals(Pair.of("HEARTS", '3'), choosenCard);
    }

    @Test
    void playCardTestOSAtPos2HasNoStarterSuitHasTrump1(){
        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "SPADES", 'A', false, SuitName.HEARTS);

        OpponentStrategy testStrategy = new OpponentStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        AutomaticPlayer testPlayer = new AutomaticPlayer(SeatName.SOUTH, "", "T83", "QT6542", "A973", testStrategy);

        Pair<String, Character> choosenCard = testPlayer.playCard(SuitName.HEARTS, testRound);
        assertEquals(Pair.of("HEARTS", '3'), choosenCard);
    }

    // második helyen, ha az induló nem figurát tett, a játékosnál pedig nincs az induló színből lap, és nincs nála adu: dob legrövidebb (nem adu) színből
    @Test
    void playCardTestSDSAtPos2HasNoStarterSuitDispose1(){
        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "SPADES", '5', false, SuitName.HEARTS);

        SimpleDeclarerStrategy testStrategy = new SimpleDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        AutomaticPlayer testPlayer = new AutomaticPlayer(SeatName.SOUTH, "", "T83", "QT6542", "A973", testStrategy);

        Pair<String, Character> choosenCard = testPlayer.playCard(SuitName.HEARTS, testRound);
        assertEquals(Pair.of("CLUBS", '3'), choosenCard);
    }

    @Test
    void playCardTestADSAtPos2HasNoStarterSuitDispose1(){
        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "SPADES", '5', false, SuitName.HEARTS);

        AdvancedDeclarerStrategy testStrategy = new AdvancedDeclarerStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        AutomaticPlayer testPlayer = new AutomaticPlayer(SeatName.SOUTH, "", "T83", "QT6542", "A973", testStrategy);

        Pair<String, Character> choosenCard = testPlayer.playCard(SuitName.HEARTS, testRound);
        assertEquals(Pair.of("CLUBS", '3'), choosenCard);
    }

    @Test
    void playCardTestODSAtPos2HasNoStarterSuitDispose1(){
        Round testRound = new Round();
        testRound.addToCards(SeatName.EAST, "SPADES", '5', false, SuitName.HEARTS);

        OpponentStrategy testStrategy = new OpponentStrategy();
        testStrategy.initializeObservedSuits(SeatName.EAST, SeatName.WEST);
        AutomaticPlayer testPlayer = new AutomaticPlayer(SeatName.SOUTH, "", "T83", "QT6542", "A973", testStrategy);

        Pair<String, Character> choosenCard = testPlayer.playCard(SuitName.HEARTS, testRound);
        assertEquals(Pair.of("CLUBS", '3'), choosenCard);
    }
}
