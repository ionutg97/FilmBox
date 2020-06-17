package io.javabrains.dataBaseMovie.controller;

import io.javabrains.dataBaseMovie.basicSecurity.TokenSubject;
import io.javabrains.dataBaseMovie.basicSecurity.Utils;
import io.javabrains.dataBaseMovie.configuration.AsynchConfiguration;
import io.javabrains.dataBaseMovie.dto.MovieDTO;
import io.javabrains.dataBaseMovie.models.Movie;
import io.javabrains.dataBaseMovie.models.replication.MovieReplication;
import io.javabrains.dataBaseMovie.models.replication.RestTemplateSubject;
import io.javabrains.dataBaseMovie.service.DataBaseServices;
import io.javabrains.dataBaseMovie.utils.ControllerRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Slf4j
@RestController
@RequestMapping("/movie")
public class DataBaseController {

    private RestTemplate restTemplate;
    private BlockingQueue<RestTemplateSubject> replicationQueue;

    @Autowired
    private AsynchConfiguration asynchConfiguration;

    @Autowired
    private DataBaseServices dataBaseServices;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @PostConstruct
    void init() {
        restTemplate = new RestTemplate();
        replicationQueue=new ArrayBlockingQueue<RestTemplateSubject>(10);
    }


    @PostMapping
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie, @RequestHeader("Authorization") String token) {

        TokenSubject tokenSubject = Utils.validateRequestUsingJWT(token);
        log.info("Data base movie controller save new Movie metadata in SQL data base!");
        RestTemplateSubject restTemplateSubject = new RestTemplateSubject();

        if (tokenSubject != null && activeProfile.equals("dev")) {
            Movie movieSaved = dataBaseServices.saveMovie(movie);
//            restTemplate.postForEntity("http://movie-commentary-service:8087/movie/replication",
//                    ControllerRequestUtils.createRequest(new MovieReplication(movieSaved.getVideoId())),
//                    MovieReplication.class);
            restTemplateSubject.setUrlName("http://movie-commentary-service:8087/movie/replication");
            restTemplateSubject.setMovieReplication(new MovieReplication(movieSaved.getVideoId()));
            this.replicationQueue.add(restTemplateSubject);

            return new ResponseEntity(movieSaved, HttpStatus.OK);
        } else if (tokenSubject != null && activeProfile.equals("default")) {
            Movie movieSaved = dataBaseServices.saveMovie(movie);
//            restTemplate.postForEntity("http://localhost:8087/movie/replication",
//                    ControllerRequestUtils.createRequest(new MovieReplication(movieSaved.getVideoId())),
//                    MovieReplication.class);
            restTemplateSubject.setUrlName("http://localhost:8087/movie/replication");
            restTemplateSubject.setMovieReplication(new MovieReplication(movieSaved.getVideoId()));
            this.replicationQueue.add(restTemplateSubject);

            return new ResponseEntity(movieSaved, HttpStatus.OK);
        } else
            return new ResponseEntity<Movie>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovie(@RequestHeader("Authorization") String token) {
        TokenSubject tokenSubject = Utils.validateRequestUsingJWT(token);
        log.info("Data base movie controller get all Movies metadata from SQL data base!");

        if (tokenSubject != null) {
            return new ResponseEntity<List<MovieDTO>>(dataBaseServices.findAllMovie(), HttpStatus.OK);
        }
        return new ResponseEntity<List<MovieDTO>>(HttpStatus.UNAUTHORIZED);
    }

    @Async("asyncExecutor")
    public void asyncMethodRestTemplate() {
        while(true) {
            if(!replicationQueue.isEmpty()) {
                try {
                    Thread.sleep(10000);
                    RestTemplateSubject subject = replicationQueue.peek();
                    ResponseEntity<MovieReplication> movieReplicationSaved =
                            restTemplate.postForEntity(subject.getUrlName(),
                                    ControllerRequestUtils.createRequest(subject.getMovieReplication()),
                                    MovieReplication.class);
                    if(movieReplicationSaved.getStatusCode()==HttpStatus.CREATED) {
                        replicationQueue.take();
                        log.info("Succesfully movie replication!");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch(ResourceAccessException e) {
                    log.error(e.getMessage());
                    //e.printStackTrace();
                }
                catch(Exception e){
                    log.error(e.getMessage());
                }
            }
        }
    }
}
