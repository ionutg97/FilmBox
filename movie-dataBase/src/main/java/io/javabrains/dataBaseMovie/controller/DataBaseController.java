package io.javabrains.dataBaseMovie.controller;

import io.javabrains.dataBaseMovie.basicSecurity.TokenSubject;
import io.javabrains.dataBaseMovie.basicSecurity.Utils;
import io.javabrains.dataBaseMovie.models.Movie;
import io.javabrains.dataBaseMovie.models.replication.MovieReplication;
import io.javabrains.dataBaseMovie.service.DataBaseServices;
import io.javabrains.dataBaseMovie.utils.ControllerRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequestMapping("/movie")
public class DataBaseController {

    private RestTemplate restTemplate;

    @Autowired
    private DataBaseServices dataBaseServices;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @PostConstruct
    void init() {
        restTemplate = new RestTemplate();
    }


    @PostMapping
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie, @RequestHeader("Authorization") String token) {

        TokenSubject tokenSubject = Utils.validateRequestUsingJWT(token);
        log.info("Data base movie service save new Movie metadata in SQL data base!");
        Movie movieSaved = dataBaseServices.saveMovie(movie);
        if (tokenSubject != null && activeProfile.equals("dev")) {

            restTemplate.postForEntity("http://movie-commentary-service:8087/movie/replication",
                    ControllerRequestUtils.createRequest(new MovieReplication(movieSaved.getVideoId())),
                    MovieReplication.class);

            return new ResponseEntity(movieSaved, HttpStatus.OK);
        } else if (tokenSubject != null && activeProfile.equals("default")) {

            restTemplate.postForEntity("http://localhost:8087/movie/replication",
                    ControllerRequestUtils.createRequest(new MovieReplication(movieSaved.getVideoId())),
                    MovieReplication.class);

            return new ResponseEntity(movieSaved, HttpStatus.OK);
        } else
            return new ResponseEntity<Movie>(HttpStatus.UNAUTHORIZED);
    }

}
