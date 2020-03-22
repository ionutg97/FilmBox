package io.javabrains.mongoDataBaseMovieservice.controller;

import io.javabrains.mongoDataBaseMovieservice.dto.ListChunckDTO;
import io.javabrains.mongoDataBaseMovieservice.service.PersistanceMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
