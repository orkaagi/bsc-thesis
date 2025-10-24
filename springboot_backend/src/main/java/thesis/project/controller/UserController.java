package thesis.project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import thesis.project.entity.User;
import thesis.project.service.RequestValidationService;
import thesis.project.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestValidationService requestValidationService;

    @GetMapping("/users")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<Iterable<User>>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findOne(@PathVariable int id) {
        return new ResponseEntity<User>(userService.findUserById(id), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<?> newUser(@Valid @RequestBody User newUser, BindingResult result) {
        Map<String, String> errorMap = requestValidationService.handleValidationErrors(result);
        if (errorMap != null) return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<User>(userService.saveUser(newUser), HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> replaceUser(@PathVariable int id, @Valid @RequestBody User newUser, BindingResult result) {
        Map<String, String> errorMap = requestValidationService.handleValidationErrors(result);
        if (errorMap != null) return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        newUser.setId(id);
        userService.saveUser(newUser);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @GetMapping("/users/jpql/{name}")
    public ResponseEntity<?> findUserByUsername(@PathVariable String name) {
        return new ResponseEntity<User>(userService.findUserByUsernameWithJPQL(name), HttpStatus.OK);
    }
}
