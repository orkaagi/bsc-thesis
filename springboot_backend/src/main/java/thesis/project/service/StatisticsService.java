package thesis.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thesis.project.entity.DeclalerLevel;
import thesis.project.entity.GameMode;
import thesis.project.entity.Statistics;
import thesis.project.repository.StatisticsRepository;

import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    public Statistics saveStatistics(Statistics newStatistics) {
        return statisticsRepository.save(newStatistics);
    }

    public Iterable<Statistics> findAllStatistics() {
        return statisticsRepository.findAll();
    }

    public Statistics findStatisticsById(int statisticsId) {
        return statisticsRepository.findById(statisticsId).orElse(null);
    }

    public void deleteStatisticsById(int statisticsId){
        try {
            statisticsRepository.deleteById(statisticsId);
        } catch (Exception e) {
            System.err.println("Unable to delete Statistics with ID: " + statisticsId);
        }
    }

    public List<Statistics> findResultsByUsername(String username){
        return statisticsRepository.findResultsByUsername(username);
    }

    public Statistics findByGameIdAndUsername(int gameId, String username){
        return statisticsRepository.findByGameIdAndUsername(gameId, username).get(0);
    }

    public Statistics findByProperties(int gameId, String username, GameMode mode, DeclalerLevel level) {
        return statisticsRepository.findByProperties(gameId, username, mode, level).get(0);
    }
}
