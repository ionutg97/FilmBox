package io.javabrains.moviecommentaryservice.controller;

import io.javabrains.moviecommentaryservice.models.Comment;
import io.javabrains.moviecommentaryservice.basicSecurity.TokenSubject;
import io.javabrains.moviecommentaryservice.basicSecurity.Utils;
import io.javabrains.moviecommentaryservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> save(@RequestBody Comment comment, @RequestHeader("Authorization") String token ) {

        TokenSubject tokenSubject=Utils.validateRequestUsingJWT(token);

        if(tokenSubject!=null)
            return new ResponseEntity<Comment>(commentService.save(comment), HttpStatus.CREATED);
        else
            return new ResponseEntity<Comment>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id, @RequestHeader("Authorization") String token ) {
        TokenSubject tokenSubject=Utils.validateRequestUsingJWT(token);

        if(tokenSubject!=null) {
            if (commentService.delete(id) >= 1)
                return new ResponseEntity(HttpStatus.OK);
            else
                return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else
            return new ResponseEntity<Comment>(HttpStatus.UNAUTHORIZED);
    }


}
