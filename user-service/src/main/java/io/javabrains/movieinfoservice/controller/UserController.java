package io.javabrains.movieinfoservice.controller;

import io.javabrains.movieinfoservice.exceptions.ResourceNotFoundException;
import io.javabrains.movieinfoservice.models.User;
import io.javabrains.movieinfoservice.models.replication.UserReplication;
import io.javabrains.movieinfoservice.service.UserService;
import io.javabrains.movieinfoservice.utils.ControllerRequestUtils;
import io.javabrains.movieinfoservice.utils.ControllerResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    UserService userService;

    private RestTemplate restTemplate;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @PostConstruct
    void init() {
        restTemplate = new RestTemplate();
    }


    @PostMapping("/new_user")
    public ResponseEntity<User> save(@RequestBody User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        User userSaved = userService.save(user);

        log.info("POST request for saving user with path: {}", userSaved.getId());

        if (userSaved.getId() != null && activeProfile.equals("dev")) {
            ResponseEntity<UserReplication> userReplicationSaved =
                    restTemplate.postForEntity("http://movie-commentary-service:8087/user/replication",
                            ControllerRequestUtils.createRequest(new UserReplication(userSaved.getId())),
                            UserReplication.class);
            return new ResponseEntity<>(userSaved,
                    ControllerResponseUtils.getResponseHeaders(), HttpStatus.CREATED);
        } else {
            ResponseEntity<UserReplication> userReplicationSaved =
                    restTemplate.postForEntity("http://localhost:8087/user/replication",
                            ControllerRequestUtils.createRequest(new UserReplication(userSaved.getId())),
                            UserReplication.class);
            return new ResponseEntity<>(userSaved,
                    ControllerResponseUtils.getResponseHeaders(), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            log.info("User with id= {} was deleted", id);

            if (activeProfile.equals("dev")) {
                restTemplate.delete("http://movie-commentary-service:8087/user/replication/" + id);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                restTemplate.delete("http://localhost:8087/user/replication/" + id);
                return new ResponseEntity(HttpStatus.OK);
            }
        } catch (ResourceNotFoundException exception) {
            log.error("User with id= {} could not found!", id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<User> getProfile(@PathVariable Long id) {
        log.info("Get request to display profile informations for user with id: {}", id);
        User userDto = userService.getUserById(id);
        return new ResponseEntity<>(userDto,
                ControllerResponseUtils.getResponseHeaders(), HttpStatus.OK);
    }

    @GetMapping("/profiles")
    public ResponseEntity<Iterable<User>> getUsersProfile(@RequestParam(name = "type") String type) {
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
