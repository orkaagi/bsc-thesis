package thesis.project.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Game game;

    @ManyToOne()
    private User user;

    @Enumerated(EnumType.STRING)
    private MatchResult result = MatchResult.NO_DATA;

    private int difference;

    @Column(name="ended_at")
    private String gameEndedAt = "";

    @Column(name="game_mode")
    @Enumerated(EnumType.STRING)
    private GameMode mode;

    @Column(name="declarer_level")
    @Enumerated(EnumType.STRING)
    private DeclalerLevel level;

    public Statistics() {}

    public Statistics(User user, Game game, GameMode mode, DeclalerLevel level) {
        this.user = user;
        this.game = game;
        this.mode = mode;
        this.level = level;
    }

    public Statistics(int id, Game game, User user, int difference, MatchResult result, GameMode mode, DeclalerLevel level) {
        this.id = id;
        this.game = game;
        this.user = user;
        this.result = result;
        this.difference = difference;
        this.mode = mode;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MatchResult getResult() {
        return result;
    }

    public void setResult(MatchResult result) {
        this.result = result;
    }

    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }

    public String getGameEndedAt() {
        return gameEndedAt;
    }

    public void setGameEndedAt(String gameEndedAt) {
        this.gameEndedAt = gameEndedAt;
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public DeclalerLevel getLevel() {
        return level;
    }

    public void setLevel(DeclalerLevel level) {
        this.level = level;
    }

    public String getContract(){
        return game.getContract();
    }

    public int getGameId(){
        return game.getId();
    }
}
