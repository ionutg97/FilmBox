package io.javabrains.moviecommentaryservice.controller;

import io.javabrains.moviecommentaryservice.models.User;
import io.javabrains.moviecommentaryservice.service.UserReplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user/replication")
@RestController
public class UserReplicationController {

    @Autowired
    private UserReplicationService userReplicationService;

    @PostMapping
    public ResponseEntity<User> saveUserReplication(@RequestBody User user) {
        return new ResponseEntity<User>(userReplicationService.saveUserReplication(user), HttpStatus.CREATED);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteUserReplication(@PathVariable Long id) {
        if (userReplicationService.deleteUserReplication(id) >= 1)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
