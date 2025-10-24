package thesis.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import thesis.project.entity.Suit;

import java.util.List;

@Repository
public interface SuitRepository extends JpaRepository<Suit, Integer> {

    @Query("SELECT s FROM Suit s WHERE s.game.id = ?1")
    public List<Suit> findByGameId(int game_id);

    @Query("SELECT s FROM Suit s WHERE s.game.id = ?1 AND s.seat = ?2 AND s.suit_name = ?3")
    public Suit findByHand(int game_id, String seat, String suit_name);
}
