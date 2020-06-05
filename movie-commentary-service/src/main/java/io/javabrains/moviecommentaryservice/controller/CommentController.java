package io.javabrains.moviecommentaryservice.controller;

import io.javabrains.moviecommentaryservice.models.Comment;
import io.javabrains.moviecommentaryservice.basicSecurity.TokenSubject;
import io.javabrains.moviecommentaryservice.basicSecurity.Utils;
import io.javabrains.moviecommentaryservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity delete(@PathVariable String id, @RequestHeader("Authorization") String token ) {
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

    @GetMapping(value= "/{id}")
    public ResponseEntity<List<Comment>> getAllCommentaryForMovie(@PathVariable String id, @RequestHeader("Authorization") String token ){
        TokenSubject tokenSubject=Utils.validateRequestUsingJWT(token);

        if(tokenSubject!=null) {
            List result =commentService.getAllCommentForMovie(id);
            return new ResponseEntity<List<Comment>>(result,HttpStatus.OK);
        }
        else
            return new ResponseEntity<List<Comment>>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value= "/number/{id}")
    public ResponseEntity<Integer> getNumberOfComm(@PathVariable String id, @RequestHeader("Authorization") String token ){
        TokenSubject tokenSubject=Utils.validateRequestUsingJWT(token);

        if(tokenSubject!=null) {
            Integer result=commentService.getNumberOfComm(id);
            return new ResponseEntity<Integer>(result,HttpStatus.OK);
        }
        else
            return new ResponseEntity<Integer>(HttpStatus.UNAUTHORIZED);
    }

}
