package io.javabrains.moviecommentaryservice.persitance.mapper;

import io.javabrains.moviecommentaryservice.models.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentRowMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet resultSet,int i) throws SQLException {
        Comment comment = new Comment();

        comment.setId(resultSet.getLong("id"));
        comment.setContent(resultSet.getString("content"));
        comment.setIdUser(resultSet.getLong("id_user"));

        return comment;
    }
}
