package io.javabrains.dataBaseMovie.service;

import io.javabrains.dataBaseMovie.dto.MovieDTO;
import io.javabrains.dataBaseMovie.models.Movie;
import io.javabrains.dataBaseMovie.repository.DataBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataBaseServices {

    private String URL="http://localhost:8088/mongo?video=";

    @Autowired
    private DataBaseRepository dataBaseRepository;


    public Movie saveMovie(Movie movie){
        return dataBaseRepository.save(movie);
    }

    public List<MovieDTO> findAllMovie(){
        List<MovieDTO> list= dataBaseRepository.findAll();
        list.forEach(f -> f.setVideoId(URL+f.getVideoId()));

        return list;
    }

}
