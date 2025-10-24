package thesis.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thesis.project.classes.Match;
import thesis.project.entity.DeclalerLevel;
import thesis.project.entity.GameMode;
import thesis.project.entity.MatchResult;
import thesis.project.service.MainService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/game")
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping("/start/params")
    public ResponseEntity<?> loadGameWithParams(@RequestParam String level, @RequestParam String mode, @RequestParam String trumpType, @RequestParam int id){
        System.out.println(level);
        return new ResponseEntity<Match>(mainService.loadParameterizedMatch(level, mode, trumpType, id), HttpStatus.OK);
    }

    @GetMapping("/play/card")
    public ResponseEntity<?> updateUserHand(@RequestParam String seat, @RequestParam String suit, @RequestParam String card, @RequestParam int ind, @RequestParam String mode){
        return new ResponseEntity<Match>(mainService.updateUserHand(seat, suit, card.charAt(0), ind, mode), HttpStatus.OK);
    }

    @GetMapping("/play/automatic")
    public ResponseEntity<?> updateAutomaticHand(@RequestParam String seat, @RequestParam String mode){
        return new ResponseEntity<Match>(mainService.updateAutomaticHand(seat, mode), HttpStatus.OK);
    }

    @GetMapping("/over")
    public ResponseEntity<?> readResult(){
        return new ResponseEntity<Pair<MatchResult, Integer>>(mainService.manageGameOverNotAuthenticated(), HttpStatus.OK);
    }

    @GetMapping("/over/auth")
    public ResponseEntity<?> updateStatistics(@RequestParam int gameId, @RequestParam String username, @RequestParam String mode, @RequestParam String level){
        return new ResponseEntity<Pair<MatchResult, Integer>>(mainService.manageGameOverAuthenticated(gameId, username, GameMode.valueOf(mode), DeclalerLevel.valueOf(level)), HttpStatus.OK);
    }
}
