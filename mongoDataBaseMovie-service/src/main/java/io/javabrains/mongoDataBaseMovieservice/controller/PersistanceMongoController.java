package io.javabrains.mongoDataBaseMovieservice.controller;

import io.javabrains.mongoDataBaseMovieservice.dto.ListChunckDTO;
import io.javabrains.mongoDataBaseMovieservice.service.PersistanceMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/mongo")
public class PersistanceMongoController {

    @Autowired
    PersistanceMongoService persistanceMongoService;

    @PostMapping("/save_chuncks")
    public ResponseEntity<ListChunckDTO> saveChuncks(@RequestBody ListChunckDTO chuncks) {
        ListChunckDTO itemSaved = persistanceMongoService.saveChuncks(chuncks);
        return new ResponseEntity(itemSaved, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/video")
    public ResponseEntity<byte[]> getChuncks(@RequestParam(name="video") String id, HttpServletResponse response){
        ResponseEntity<byte[]> result = null;
        byte[] video = Base64.decodeBase64(persistanceMongoService.getChuncks(id).getVideoChunck());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(video.length);
        response.setStatus(HttpStatus.OK.value());
        result = new ResponseEntity<byte[]>(video, headers, HttpStatus.OK);
        return result;
    }
}
