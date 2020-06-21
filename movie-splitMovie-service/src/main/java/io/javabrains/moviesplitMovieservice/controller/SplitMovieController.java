package io.javabrains.moviesplitMovieservice.controller;

import io.javabrains.moviesplitMovieservice.basicSecurity.TokenSubject;
import io.javabrains.moviesplitMovieservice.basicSecurity.Utils;
import io.javabrains.moviesplitMovieservice.dto.ListOfChunckDTO;
import io.javabrains.moviesplitMovieservice.models.Chunck;
import io.javabrains.moviesplitMovieservice.models.SplitMovie;
import io.javabrains.moviesplitMovieservice.service.SplitMovieService;
import io.javabrains.moviesplitMovieservice.utils.ControllerRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/split_movie")
public class SplitMovieController {

    private SplitMovieService splitMovieService;
    private RestTemplate restTemplate;

    @Value("${spring.profiles.active}")
    private String activeProfile;

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
        return new ResponseEntity<>("Hello world " + activeProfile, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<SplitMovie> splitMovieInChunk(@RequestParam("pathFileName") String pathName,
                                                        @RequestHeader("Authorization") String token) {
        String path;
        if (this.activeProfile.equals("dev"))
            path = setPathForProfile(pathName);
        else
            path = pathName;

        TokenSubject tokenSubject = Utils.validateRequestUsingJWT(token);
        if (tokenSubject!=null)
        {
            log.info("controller split movie file into many files of fixed size");

            SplitMovie movieSplited = splitMovieService.splitMovie(path);
            splitMovieService.saveChuncksInFile(movieSplited, path);
            movieSplited.setIdUser(tokenSubject.getId());

            //create request for Database movie
            JSONObject body = getDataBaseBodyRequestJSON(movieSplited);
            HttpEntity<String> request = ControllerRequestUtils.createRequest(body,token);

            //create requeste for MongoDatabase chunck
            JSONObject bodyMongo = getMongoBodyRequestJSON(movieSplited);
            HttpEntity<String> requestMongo = ControllerRequestUtils.createRequest(bodyMongo,token);
            if (activeProfile.equals("dev")) {

                //storage metadata in DataBaseMovie
                restTemplate.postForEntity("http://movieDataBase-service:8086/movie", request, String.class);

                //storage chunck in MongoDataBase
                ResponseEntity<ListOfChunckDTO> listOfChunck = restTemplate.postForEntity("http://mongodb-service:8088/mongo/save_chuncks",
                        requestMongo, ListOfChunckDTO.class);
                movieSplited.setListOfChunks(listOfChunck.getBody().getListOfChuncks());

                return new ResponseEntity<>(movieSplited, HttpStatus.OK);
            } else {
                //storage metadata in DataBaseMovie
                restTemplate.postForEntity("http://localhost:8086/movie", request, String.class);

                //storage chunck in MongoDataBase
                ResponseEntity<ListOfChunckDTO> listOfChunck = restTemplate.postForEntity("http://localhost:8088/mongo/save_chuncks",
                        requestMongo, ListOfChunckDTO.class);
                movieSplited.setListOfChunks(listOfChunck.getBody().getListOfChuncks());

                return new ResponseEntity<>(movieSplited, HttpStatus.OK);
            }
    }
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

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
            log.error("Create mongo data base request json!");
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
            body.put("idUser", movieSplited.getIdUser());

        } catch (JSONException e) {
            log.error("Create data base request json!");
            e.printStackTrace();
        }
        return body;
    }

    @Profile("dev")
    private String setPathForProfile(String pathName) {
        List<String> words = new ArrayList<>();
        char[] splitPath = pathName.toCharArray();
        String word = "";

        for (int i = 0; i < splitPath.length; i++) {
            if (splitPath[i] == '\\') {
                words.add(word);
                word = "";
            } else
                word += splitPath[i];
        }
        words.add(word);
        return "/home/user/videos/" + words.get(words.size() - 1);
    }

}
