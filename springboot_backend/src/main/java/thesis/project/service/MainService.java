package thesis.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import thesis.project.classes.*;
import thesis.project.entity.*;
import thesis.project.exception.CustomEntityNotFoundException;
import thesis.project.repository.GameRepository;
import thesis.project.repository.SuitRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class MainService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private SuitRepository suitRepository;

    @Autowired
    private StatisticsService statisticsService;

    private Match match;

    /**
     * Populates the match attribute by querying a game from the database using the provided parameters.
     *
     * @param level  declarer level
     * @param mode  game mode
     * @param trumpType  COLOR, NOTRUMP or empty string, irrelevant if id is not -1
     * @param id   id of the game which should be loaded, random game if id is -1
     * @return the match attribute
     */
    public Match loadParameterizedMatch(String level, String mode, String trumpType, int id) {

        System.out.println("jatek fajta: " + trumpType + ", id=" + id);

        int gameId = determineId(id, trumpType);
        Game game = gameRepository.findById(gameId).orElseThrow(
                () -> new CustomEntityNotFoundException("Game does not exist with the given ID!")
        );

        Strategy northStrategy;
        Strategy eastStrategy;
        if (Objects.equals(level, "BEGINNER")) {
            northStrategy = new SimpleDeclarerStrategy();
            eastStrategy = new SimpleDeclarerStrategy();
        } else {
            northStrategy = new AdvancedDeclarerStrategy();
            eastStrategy = new AdvancedDeclarerStrategy();
        }

        AutomaticPlayer northHand = new AutomaticPlayer(
                SeatName.NORTH,
                suitRepository.findByHand(gameId, "N", "S").getCard_values(),
                suitRepository.findByHand(gameId, "N", "H").getCard_values(),
                suitRepository.findByHand(gameId, "N", "D").getCard_values(),
                suitRepository.findByHand(gameId, "N", "C").getCard_values(),
                northStrategy);
        AutomaticPlayer southHand = new AutomaticPlayer(
                SeatName.SOUTH,
                suitRepository.findByHand(gameId, "S", "S").getCard_values(),
                suitRepository.findByHand(gameId, "S", "H").getCard_values(),
                suitRepository.findByHand(gameId, "S", "D").getCard_values(),
                suitRepository.findByHand(gameId, "S", "C").getCard_values(),
                eastStrategy);

        UserPlayer eastHand = new UserPlayer(
                SeatName.EAST,
                suitRepository.findByHand(gameId, "E", "S").getCard_values(),
                suitRepository.findByHand(gameId, "E", "H").getCard_values(),
                suitRepository.findByHand(gameId, "E", "D").getCard_values(),
                suitRepository.findByHand(gameId, "E", "C").getCard_values());

        Player westHand;
        if (Objects.equals(mode, "DEFENSE")) {
            Strategy westStrategy = new OpponentStrategy();
             westHand = new AutomaticPlayer(
                    SeatName.WEST,
                    suitRepository.findByHand(gameId, "W", "S").getCard_values(),
                    suitRepository.findByHand(gameId, "W", "H").getCard_values(),
                    suitRepository.findByHand(gameId, "W", "D").getCard_values(),
                    suitRepository.findByHand(gameId, "W", "C").getCard_values(),
                    westStrategy);
            ((AutomaticPlayer) westHand).getStrategy().initializeObservedSuits(SeatName.NORTH, SeatName.SOUTH);
            ((AutomaticPlayer) westHand).getStrategy().initializeDummyHand(southHand.getSuits());
        } else {
            westHand = new UserPlayer(
                    SeatName.WEST,
                    suitRepository.findByHand(gameId, "W", "S").getCard_values(),
                    suitRepository.findByHand(gameId, "W", "H").getCard_values(),
                    suitRepository.findByHand(gameId, "W", "D").getCard_values(),
                    suitRepository.findByHand(gameId, "W", "C").getCard_values());
        }

        match = new Match(0, 0, game.getContract(), gameId);

        SuitName trump = match.getTrump();

        northHand.getStrategy().initializeObservedSuits(SeatName.WEST, SeatName.EAST);
        northHand.getStrategy().initializeDummyHand(southHand.getSuits());
        northHand.getStrategy().setStrongestSuit(trump, northHand.getSuits(), southHand.getSuits());
        northHand.getStrategy().initializeDeclarerTrumpNumber(trump, northHand.getSuits(), southHand.getSuits());

        southHand.getStrategy().initializeObservedSuits(SeatName.WEST, SeatName.EAST);
        southHand.getStrategy().initializeDummyHand(northHand.getSuits());
        southHand.getStrategy().setStrongestSuit(trump, southHand.getSuits(), northHand.getSuits());
        southHand.getStrategy().initializeDeclarerTrumpNumber(trump, southHand.getSuits(), northHand.getSuits());

        match.updatePlayers(SeatName.NORTH, northHand);
        match.updatePlayers(SeatName.SOUTH, southHand);
        match.updatePlayers(SeatName.EAST, eastHand);
        match.updatePlayers(SeatName.WEST, westHand);

        match.resetRound();



        return match;
    }

    /**
     * Propagates control flow to <code>match.playUserCard</code> for playing a card, after checking if the provided seat name and mode are valid.
     *
     * @param seat  seat name of the player who wants to play a card
     * @param suit  suit name of the suit from which the player wants to play a card
     * @param card  value of the card which the player wants to play
     * @param ind   the position of the card which the player wants to play within its suit
     * @param mode  game mode
     * @return a match object, or null, if match object is null
     */
    public Match updateUserHand(String seat, String suit, char card, int ind, String mode) {
        if (Objects.equals(seat, SeatName.EAST.name()) || (Objects.equals(mode, GameMode.ANALYSIS.name()) && Objects.equals(seat, SeatName.WEST.name()))) {
            if (match != null){
                match.playUserCard(SeatName.valueOf(seat), suit, card, ind);
            }
        }
        return match;
    }

    /**
     * Propagates control flow to <code>match.playAutomaticCard</code> for playing a card, after checking if the provided seat name and mode are valid.
     *
     * @param seat  seat name of the player who wants to play a card
     * @param mode  game mode
     * @return a match object, or null, if match object is null
     */
    public Match updateAutomaticHand(String seat, String mode) {
        if(Objects.equals(seat, SeatName.NORTH.name()) || Objects.equals(seat, SeatName.SOUTH.name())
                || (Objects.equals(mode, GameMode.DEFENSE.name()) && Objects.equals(seat, SeatName.WEST.name()))) {
            if (match != null){
                match.playAutomaticCard(SeatName.valueOf(seat));
            }
        }
        return match;
    }

    public Pair<MatchResult, Integer> manageGameOverNotAuthenticated() {
        int declarerGoal = match.getBid();
        int difference = match.getScore_NS() - declarerGoal;

        MatchResult result = MatchResult.LOST;
        if(difference < 0) {
            result = MatchResult.WON;
        }

        return Pair.of(result, difference);
    }

    public Pair<MatchResult, Integer> manageGameOverAuthenticated(int gameId, String username, GameMode mode, DeclalerLevel level) {
        int declarerGoal = match.getBid();
        int difference = match.getScore_NS() - declarerGoal;

        MatchResult result = MatchResult.LOST;
        if(difference < 0) {
            result = MatchResult.WON;
        }

        LocalDateTime gameEndsAt = LocalDateTime.now();
        DateTimeFormatter gameEndsAtFormat = DateTimeFormatter.ofPattern("MM.dd. HH:mm");
        String formattedDate = gameEndsAt.format(gameEndsAtFormat);

        Statistics stat = statisticsService.findByProperties(gameId, username, mode, level);
        stat.setDifference(difference);
        stat.setResult(result);
        stat.setGameEndedAt(formattedDate);
        statisticsService.saveStatistics(stat);

        return Pair.of(result, difference);
    }

    /**
     * Returns the id parameter, if it's a valid game id. Otherwise, chooses a random game id, based on the parameters.
     *
     * @param id   id of the game which should be loaded, random game if id is -1
     * @param trumpType  COLOR, NOTRUMP or empty string, irrelevant if id is not -1     *
     * @return the match attribute
     */
    private int determineId(int id, String trumpType) {
        int randomId = id;
        int numberOfGames = gameRepository.numberOfGames();
        if (id < 0 || numberOfGames < id) {
            if (trumpType.isEmpty()) {
                randomId = (int) (Math.random() * numberOfGames) + 1;
            } else {
                List<Integer> ids = (trumpType.equals("NOTRUMP")) ? gameRepository.NoTrumpIds() : gameRepository.ColorTrumpIds();
                int chooseRandom = (int) (Math.random() * (ids.size() - 1));
                randomId = ids.get(chooseRandom);
            }
        }
        return randomId;
    }
}
