package thesis.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import thesis.project.entity.*;
import thesis.project.exception.UniqueConstraintViolationException;
import thesis.project.exception.CustomEntityNotFoundException;
import thesis.project.repository.GameRepository;
import thesis.project.repository.UserRepository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private Argon2PasswordEncoder argon2PasswordEncoder;

    public User saveUser(User newUser) {

        newUser.setPassword(argon2PasswordEncoder.encode(newUser.getPassword()));

        List<Statistics> newStats = new ArrayList<>();
        List<Game> games = gameRepository.findAllGames();
        for (Game game : games) {
            Statistics stat1 = new Statistics(newUser, game, GameMode.DEFENSE, DeclalerLevel.BEGINNER);
            newStats.add(stat1);
            Statistics stat2 = new Statistics(newUser, game, GameMode.DEFENSE, DeclalerLevel.MEDIUM);
            newStats.add(stat2);
            Statistics stat3 = new Statistics(newUser, game, GameMode.ANALYSIS, DeclalerLevel.BEGINNER);
            newStats.add(stat3);
            Statistics stat4 = new Statistics(newUser, game, GameMode.ANALYSIS, DeclalerLevel.MEDIUM);
            newStats.add(stat4);
        }
        newUser.setStatistics(newStats);
        User savedUser = userRepository.save(newUser);

        for(Statistics stat : newStats){
            statisticsService.saveStatistics(stat);
        }

        try {
            return savedUser;
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintViolationException("Username '" + newUser.getUsername() + "' is already taken!");
        }
    }

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomEntityNotFoundException("User with ID '" + userId + "' does not exist!"));
    }

    public void deleteUserById(int userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            System.err.println("Unable to delete User with ID: " + userId);
        }
    }

    public User findUserByUsernameWithJPQL(String username) {
        return userRepository.findUserByUsernameWithJPQL(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(MessageFormat.format("User with username {0} cannot be found.", username)));
    }
}
