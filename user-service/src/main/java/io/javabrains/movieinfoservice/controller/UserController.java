package io.javabrains.movieinfoservice.controller;

import io.javabrains.movieinfoservice.models.User;
import io.javabrains.movieinfoservice.service.UserService;
import io.javabrains.movieinfoservice.utils.ControllerResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/new_user")
    public ResponseEntity<User> save(@RequestBody User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        User userSaved = userService.save(user);
        log.info("POST request for saving user with path: {}", userSaved.getId());
        return new ResponseEntity<>(userSaved,
                ControllerResponseUtils.getResponseHeaders(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<User> getProfile(@PathVariable Integer id) {
        log.info("Get request to display profile informations for user with id: {}", id);
        User userDto = userService.getUserById(id);
        return new ResponseEntity<>(userDto,
                ControllerResponseUtils.getResponseHeaders(), HttpStatus.OK);
    }

    @GetMapping("/profiles")
    public ResponseEntity<Iterable<User>> getUsersProfile(@RequestParam(name="type") String type) {
        log.info("Get request to display all profile informations for users by type ");
        Iterable<User> userDto = userService.findAllUserByType(type);
        return new ResponseEntity<>(userDto,
                ControllerResponseUtils.getResponseHeaders(), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Iterable<User>> getAllUsersProfile() {
        log.info("Get request to display all profile informations for users ");
        Iterable<User> userDto = userService.findAll();
        return new ResponseEntity<>(userDto,
                ControllerResponseUtils.getResponseHeaders(), HttpStatus.OK);
    }


}
