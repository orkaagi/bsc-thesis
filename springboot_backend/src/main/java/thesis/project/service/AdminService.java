package thesis.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thesis.project.entity.Game;
import thesis.project.entity.Suit;
import thesis.project.repository.GameRepository;
import thesis.project.repository.SuitRepository;

@Service
public class AdminService {

    @Autowired
    private SuitRepository suitRepository;

    @Autowired
    private GameRepository gameRepository;

    public Suit saveSuit(Suit newSuit) {
        return suitRepository.save(newSuit);
    }

    public Iterable<Suit> findAllSuits() {
        return suitRepository.findAll();
    }

    public Suit findSuitById(int suitId) {
        return suitRepository.findById(suitId).orElse(null);
    }

    public void deleteSuitById(int suitId){
        try {
            suitRepository.deleteById(suitId);
        } catch (Exception e) {
            System.err.println("Unable to delete Suit with ID: " + suitId);
        }
    }

    public Game saveGame(Game newGame) {
        return gameRepository.save(newGame);
    }

    public Iterable<Game> findAllGames() {
        return gameRepository.findAll();
    }

    public Game findGameById(int gameId) {
        return gameRepository.findById(gameId).orElse(null);
    }

    public void deleteGameById(int gameId){
        try {
            gameRepository.deleteById(gameId);
        } catch (Exception e) {
            System.err.println("Unable to delete Game with ID: " + gameId);
        }
    }
}
