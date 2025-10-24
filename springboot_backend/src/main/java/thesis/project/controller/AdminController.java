package thesis.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thesis.project.entity.Game;
import thesis.project.entity.Suit;
import thesis.project.service.AdminService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/suit")
    public ResponseEntity<?> findAllSuits(){
        return new ResponseEntity<Iterable<Suit>>(adminService.findAllSuits(), HttpStatus.OK);
    }

    @GetMapping("/suit/{id}")
    public ResponseEntity<?> getSuitById(@PathVariable int id){
        return new ResponseEntity<Suit>(adminService.findSuitById(id), HttpStatus.OK);
    }

    @PostMapping("/suit")
    public ResponseEntity<?> addSuit(@RequestBody Suit newSuit) {
        return new ResponseEntity<Suit>(adminService.saveSuit(newSuit), HttpStatus.CREATED);
    }

    @DeleteMapping("/suit/{id}")
    public ResponseEntity<?> deleteSuit(@PathVariable int id) {
        adminService.deleteSuitById(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/game")
    public ResponseEntity<?> findAllGames(){
        return new ResponseEntity<Iterable<Game>>(adminService.findAllGames(), HttpStatus.OK);
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<?> getGameById(@PathVariable int id){
        return new ResponseEntity<Game>(adminService.findGameById(id), HttpStatus.OK);
    }

    @PostMapping("/game")
    public ResponseEntity<?> addGame(@Valid @RequestBody Game newGame) {
        return new ResponseEntity<Game>(adminService.saveGame(newGame), HttpStatus.CREATED);
    }

    @DeleteMapping("/game/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable int id) {
        adminService.deleteGameById(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
