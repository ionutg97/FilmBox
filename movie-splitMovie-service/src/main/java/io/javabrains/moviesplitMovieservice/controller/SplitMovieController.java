package io.javabrains.moviesplitMovieservice.controller;

import io.javabrains.moviesplitMovieservice.models.SplitMovie;
import io.javabrains.moviesplitMovieservice.service.SplitMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/split_movie")
public class SplitMovieController {

    private SplitMovieService splitMovieService;

    @Autowired
    public SplitMovieController(SplitMovieService splitMovieService)
    {
        this.splitMovieService=splitMovieService;
    }

    @GetMapping("/verify")
    public ResponseEntity<String> getActivationLinkStatus() {
        //log.info("Verify user activation link status for link : {}", link);
        return new ResponseEntity<>("Hello world", HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<SplitMovie> getSplitMovieInFiles(@RequestParam("pathFileName") String pathName){
        log.info("controller split movie file into many files of fixed size");
        return new ResponseEntity<>(splitMovieService.splitMovie1(pathName),HttpStatus.OK);
    }


}
