package io.javabrains.moviecommentaryservice.controller;

import io.javabrains.moviecommentaryservice.models.Comment;
import io.javabrains.moviecommentaryservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class CommentWebSocketController {

    @Autowired
    CommentService commentService;

    @MessageMapping("/comment")
    @SendTo("/topic/comment")
    public Comment addComment(Comment comment) throws Exception {

        commentService.save(comment);

        Thread.sleep(1000); // simulated delay
        return Comment.builder()
                .content(HtmlUtils.htmlEscape(comment.getContent()))
                .idMovie(comment.getIdMovie())
                .idUser(comment.getIdUser())
                .build();
    }
}
