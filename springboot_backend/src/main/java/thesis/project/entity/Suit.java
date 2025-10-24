package thesis.project.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Suit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String seat;

    private String suit_name;

    @Size(max=13, message="A suit has at most 13 cards")
    private String card_values;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Game game;

    public Suit() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getSuit_name() {
        return suit_name;
    }

    public void setSuit_name(String suit_name) {
        this.suit_name = suit_name;
    }

    public String getCard_values() {
        return card_values;
    }

    public void setCard_values(String card_values) {
        this.card_values = card_values;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
