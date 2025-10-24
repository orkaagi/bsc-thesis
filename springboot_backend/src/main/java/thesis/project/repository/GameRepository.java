package thesis.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import thesis.project.entity.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query("SELECT g FROM Game g")
    public List<Game> findAllGames();

    @Query("SELECT COUNT(g) FROM Game g")
    public int numberOfGames();

    @Query("SELECT g.id FROM Game g WHERE g.contract LIKE '%NT'")
    public List<Integer> NoTrumpIds();

    @Query("SELECT g.id FROM Game g WHERE g.contract NOT LIKE '%NT'")
    public List<Integer> ColorTrumpIds();

}