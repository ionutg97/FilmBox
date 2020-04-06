package io.javabrains.moviesplitMovieservice.controller;

import io.javabrains.moviesplitMovieservice.models.Chunck;
import io.javabrains.moviesplitMovieservice.models.SplitMovie;
import io.javabrains.moviesplitMovieservice.service.SplitMovieService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.codec.Utf8;
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
    public SplitMovieController(SplitMovieService splitMovieService) {
        this.splitMovieService = splitMovieService;
    }

    @PostConstruct
    void init() {
        restTemplate = new RestTemplate();
    }

    @GetMapping("/verify")
    public ResponseEntity<String> getActivationLinkStatus() {
        log.info("Verify service");
        return new ResponseEntity<>("Hello world", HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<SplitMovie> splitMovieInChunk(@RequestParam("pathFileName") String pathName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        log.info("controller split movie file into many files of fixed size");

        SplitMovie movieSplited = splitMovieService.splitMovie(pathName);
        splitMovieService.saveChuncksInFile(movieSplited,pathName);

        //storage metadata in DataBaseMovie
//        JSONObject body = getDataBaseBodyRequestJSON(movieSplited);
//        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
//        restTemplate.postForEntity("http://localhost:8086/movie", request, String.class);

        //storage chuncks in MongoDataBaseMovie
        JSONObject bodyMongo = getMongoBodyRequestJSON(movieSplited);
        HttpEntity<String> requestMongo = new HttpEntity<>(bodyMongo.toString(), headers);
        restTemplate.postForEntity("http://localhost:8087/mongo/save_chuncks", requestMongo, String.class);

        return new ResponseEntity<>(movieSplited, HttpStatus.OK);
    }


    private JSONObject getMongoBodyRequestJSON(SplitMovie movieSplited) {
        JSONObject bodyMongo = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            for (Chunck item : movieSplited.getListOfChunks()) {
                JSONObject itemJSON = new JSONObject();
                itemJSON.put("videoId", item.getVideoId());
                itemJSON.put("videoChunck", item.getVideoChunck());

                array.put(itemJSON);
            }
            bodyMongo.put("listOfChuncks", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bodyMongo;
    }

    private JSONObject getDataBaseBodyRequestJSON(SplitMovie movieSplited) {
        JSONObject body = new JSONObject();
        try {
            body.put("fileName", movieSplited.getFileName());
            body.put("numberOfFiles", movieSplited.getNumberOfFiles());
            body.put("totalSizeFile", movieSplited.getTotalSizeFile());
            body.put("chunckSize", movieSplited.getChunckSize());
            body.put("idBlobStorage", 123456);
            body.put("videoId", movieSplited.getVideoId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return body;
    }

}
