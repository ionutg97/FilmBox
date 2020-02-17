package io.javabrains.dataBaseMovie.controller;

import io.javabrains.dataBaseMovie.models.Movie;
import io.javabrains.dataBaseMovie.service.DataBaseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class DataBaseController {

    @Autowired
    private DataBaseServices dataBaseServices;

    @PostMapping
    public ResponseEntity<Movie> writeLog(@RequestBody Movie movie) {
        return new ResponseEntity(dataBaseServices.saveMovie(movie),HttpStatus.OK);
    }

}
