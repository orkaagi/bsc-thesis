package thesis.project.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min=2, max=3, message="A contract is between 2 and 3 characters long")
    private String contract;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Suit> suits;

    @OneToMany(mappedBy = "game", cascade = CascadeType.PERSIST)
    private List<Statistics> statistics;

    public Game(){}

    public Game(String contract, List<Suit> suits) {
        this.contract = contract;
        this.suits = suits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public List<Suit> getSuits() {
        return suits;
    }

    public void setSuits(List<Suit> hands) {
        this.suits = hands;
    }

    public List<Statistics> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<Statistics> statistics) {
        this.statistics = statistics;
    }
}
