package io.javabrains.dataBaseMovie.service;

import io.javabrains.dataBaseMovie.models.Movie;
import io.javabrains.dataBaseMovie.repository.DataBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataBaseServices {

    @Autowired
    private DataBaseRepository dataBaseRepository;


    public Movie saveMovie(Movie movie){
        return dataBaseRepository.save(movie);
    }

}
