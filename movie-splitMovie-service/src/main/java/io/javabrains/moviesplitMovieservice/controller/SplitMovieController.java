package io.javabrains.moviesplitMovieservice.controller;

import io.javabrains.moviesplitMovieservice.models.SplitMovie;
import io.javabrains.moviesplitMovieservice.service.SplitMovieService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;


@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/split_movie")
public class SplitMovieController {

    private SplitMovieService splitMovieService;
    private RestTemplate restTemplate;

    @Autowired
    public SplitMovieController(SplitMovieService splitMovieService)
    {
        this.splitMovieService=splitMovieService;
    }

    @PostConstruct
    void init() {
        restTemplate=new RestTemplate();
    }

    @GetMapping("/verify")
    public ResponseEntity<String> getActivationLinkStatus() {
        //log.info("Verify user activation link status for link : {}", link);
        return new ResponseEntity<>("Hello world", HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<SplitMovie> getSplitMovieInFiles(@RequestParam("pathFileName") String pathName){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        log.info("controller split movie file into many files of fixed size");

        SplitMovie movieSplited = splitMovieService.splitMovie1(pathName);

        JSONObject body = new JSONObject();
        try {
            body.put("fileName", movieSplited.getFileName() );
            body.put("numberOfFiles", movieSplited.getNumberOfFiles());
            body.put("totalSizeFile", movieSplited.getTotalSizeFile() );
            body.put("chunckSize", movieSplited.getChunckSize());
            body.put("idBlobStorage",123456);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //storage data in DataBaseMovie
        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
        restTemplate.postForEntity("http://localhost:8086/movie",request,String.class );

        return new ResponseEntity<>(movieSplited,HttpStatus.OK);
    }


}
