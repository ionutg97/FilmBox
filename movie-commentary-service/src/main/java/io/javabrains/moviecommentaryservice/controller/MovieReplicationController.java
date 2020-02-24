package io.javabrains.moviecommentaryservice.controller;


import io.javabrains.moviecommentaryservice.models.Movie;
import io.javabrains.moviecommentaryservice.service.MovieReplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/movie/replication")
@RestController
public class MovieReplicationController {

    @Autowired
    private MovieReplicationService movieReplicationService;

    @PostMapping
    public ResponseEntity<Movie> saveMovieReplication(@RequestBody Movie movie) {
        return new ResponseEntity<Movie>(movieReplicationService.saveMovieReplication(movie), HttpStatus.CREATED);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteMovieReplication(@PathVariable Long id) {
        if (movieReplicationService.deleteMovieReplication(id) >= 1)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
