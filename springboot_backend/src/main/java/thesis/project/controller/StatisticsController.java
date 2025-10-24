package thesis.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import thesis.project.entity.Statistics;
import thesis.project.service.RequestValidationService;
import thesis.project.service.StatisticsService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private RequestValidationService requestValidationService;

    @GetMapping("/statistics")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<Iterable<Statistics>>(statisticsService.findAllStatistics(), HttpStatus.OK);
    }

    @PostMapping("/statistics")
    public ResponseEntity<?> newStatistics(@Valid @RequestBody Statistics newStatistics, BindingResult result) {
        Map<String, String> errorMap = requestValidationService.handleValidationErrors(result);
        if (errorMap != null) return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Statistics>(statisticsService.saveStatistics(newStatistics), HttpStatus.CREATED);
    }

    @GetMapping("/results")
    public ResponseEntity<?> findResultsByUsername(@RequestParam String username) {
        System.out.println(username);
        return new ResponseEntity<Iterable<Statistics>>(statisticsService.findResultsByUsername(username), HttpStatus.OK);
    }
}
