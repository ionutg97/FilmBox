package io.javabrains.moviecommentaryservice.service;

import io.javabrains.moviecommentaryservice.models.Movie;
import io.javabrains.moviecommentaryservice.persitance.MovieReplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class MovieReplicationService {

    @Autowired
    private MovieReplicationRepository movieReplicationRepository;

    public Movie saveMovieReplication(Movie movie) {
        return movieReplicationRepository.saveMovieReplication(movie);
    }

    public int deleteMovieReplication(String id) {
        return movieReplicationRepository.deleteMovieReplication(id);
    }
}
