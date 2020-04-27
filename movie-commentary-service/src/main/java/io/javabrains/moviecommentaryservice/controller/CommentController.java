package io.javabrains.moviecommentaryservice.controller;

import io.javabrains.moviecommentaryservice.models.Comment;
import io.javabrains.moviecommentaryservice.security.TokenSubject;
import io.javabrains.moviecommentaryservice.security.Utils;
import io.javabrains.moviecommentaryservice.service.CommentService;
import io.jsonwebtoken.Jwts;
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

        TokenSubject tokenSubject=null;
        if(token!=null){
             tokenSubject = Utils.getTokenSubjectFromJson(
                    Jwts.parser()
                            .setSigningKey(Utils.SECRET)
                            .parseClaimsJws(token.replace(Utils.TOKEN_PREFIX, ""))
                            .getBody()
                            .getSubject()
            );
        }
        System.out.println(tokenSubject);
        return new ResponseEntity<Comment>(commentService.save(comment), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (commentService.delete(id) >= 1)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
