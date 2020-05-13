package io.javabrains.mongoDataBaseMovieservice.controller;

import io.javabrains.mongoDataBaseMovieservice.basicSecurity.TokenSubject;
import io.javabrains.mongoDataBaseMovieservice.basicSecurity.Utils;
import io.javabrains.mongoDataBaseMovieservice.dto.ListChunckDTO;
import io.javabrains.mongoDataBaseMovieservice.dto.MovieIdChunckDTO;
import io.javabrains.mongoDataBaseMovieservice.service.PersistanceMongoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mongo")
public class PersistanceMongoController {

    @Autowired
    PersistanceMongoService persistanceMongoService;

    @PostMapping("/save_chuncks")
    public ResponseEntity<ListChunckDTO> saveChuncks(@RequestBody ListChunckDTO chuncks,
                                                     @RequestHeader("Authorization") String token) {
        TokenSubject tokenSubject = Utils.validateRequestUsingJWT(token);
        if (tokenSubject != null) {
            log.info("Mongo Db Service save data");
            ListChunckDTO itemSaved = persistanceMongoService.saveChuncks(chuncks);
            return new ResponseEntity(itemSaved, HttpStatus.OK);
        }
        else
        {
            log.error("Mongo Db Service Unauthorized!");
            return new ResponseEntity<ListChunckDTO>(HttpStatus.UNAUTHORIZED);
        }
    }

    /***
     * Get content for chunck.
     * @param id
     * @param response
     * @param token
     * @return
     */
    @CrossOrigin
    @GetMapping("/video")
    public ResponseEntity<byte[]> getChuncks(@RequestParam(name="video") String id,
                                             HttpServletResponse response,
                                             @RequestHeader("Authorization") String token){
        ResponseEntity<byte[]> result = null;
        TokenSubject tokenSubject = Utils.validateRequestUsingJWT(token);

        if (tokenSubject != null) {
            byte[] video = Base64.decodeBase64(persistanceMongoService.getChuncks(id).getVideoChunck());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentLength(video.length);

            response.setStatus(HttpStatus.OK.value());
            result = new ResponseEntity<byte[]>(video, headers, HttpStatus.OK);
            return result;
        }
        else
        {
            return new ResponseEntity<byte[]>(HttpStatus.UNAUTHORIZED);
        }
    }

    /***
     *  Get all id for chuncks generated.
     * @param video String which contain unique id for on video, which  is stored in data base.
     * @param token Token which verify if user is authenticated.
     * @return
     */
    @GetMapping()
    public ResponseEntity<List<String>> getListId(@RequestParam String video,
                                                  @RequestHeader("Authorization") String token){

        TokenSubject tokenSubject = Utils.validateRequestUsingJWT(token);

        if (tokenSubject != null) {
            List<String> listIdChunckDTO = persistanceMongoService.getListOfId(video);
            return new ResponseEntity<List<String>>(listIdChunckDTO, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<List<String>>(HttpStatus.UNAUTHORIZED);
        }
    }
}
