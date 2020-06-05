package io.javabrains.moviecommentaryservice.service;

import io.javabrains.moviecommentaryservice.models.Comment;
import io.javabrains.moviecommentaryservice.persitance.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public int delete(String id) {
        return commentRepository.delete(id);
    }

    public List getAllCommentForMovie(String id){
        return commentRepository.findAllCommentForMovie(id);
    }

    public Integer getNumberOfComm(String id){
        return commentRepository.findNewComment(id);
    }
}
