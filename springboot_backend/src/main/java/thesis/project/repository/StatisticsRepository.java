package thesis.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import thesis.project.entity.DeclalerLevel;
import thesis.project.entity.GameMode;
import thesis.project.entity.Statistics;

import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {

    @Query("SELECT s FROM Statistics s WHERE s.user.username = ?1")
    List<Statistics> findResultsByUsername(String username);

    @Query("SELECT s FROM Statistics s WHERE s.game.id = ?1 AND s.user.username = ?2")
    List<Statistics> findByGameIdAndUsername(int gameId, String username);

    @Query("SELECT s FROM Statistics s WHERE s.game.id = ?1 AND s.user.username = ?2 AND s.mode = ?3 AND s.level = ?4")
    List<Statistics> findByProperties(int gameId, String username, GameMode mode, DeclalerLevel level);
}
