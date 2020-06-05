package io.javabrains.moviecommentaryservice.persitance;

import io.javabrains.moviecommentaryservice.models.Comment;
import io.javabrains.moviecommentaryservice.models.Movie;
import io.javabrains.moviecommentaryservice.models.User;
import io.javabrains.moviecommentaryservice.persitance.mapper.CommentRowMapper;
import io.javabrains.moviecommentaryservice.persitance.queries.CommentQueries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class CommentRepository {

    private KeyHolder keyHolder;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    void init() {
        keyHolder = new GeneratedKeyHolder();
    }

    public Comment save(Comment comment) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("content", comment.getContent())
                .addValue("id_user", comment.getIdUser())
                .addValue("id_movie", comment.getIdMovie());

        int result = namedParameterJdbcTemplate.update(CommentQueries.INSERT_COMMENT, parameters, keyHolder);
        List<Map<String, Object>> keyList = keyHolder.getKeyList();
        Long id = (Long) keyList.get(0).get("id");
        comment.setId(id);
        log.info("Saving new comment in comments data base with id: {}", comment.getId());

        return comment;
    }

    public int delete(String id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        int result = namedParameterJdbcTemplate.update(CommentQueries.DELETE_COMMENT, parameters);
        return result;
    }

    public List<Comment> findAllCommentForMovie(String id){
        log.info("Retrieving all commentary for one movie!");
        MapSqlParameterSource parameter= new MapSqlParameterSource()
                .addValue("id", id);
        List<Comment> listComment;
        listComment = namedParameterJdbcTemplate
                .query(CommentQueries.GET_COMMENT_FOR_MOVIE,parameter, new CommentRowMapper());

        return listComment;
    }

    public Integer findNewComment(String id){
        log.info("Retrieving the number of commentary for one movie!");
        MapSqlParameterSource parameter= new MapSqlParameterSource()
                .addValue("id", id);
        Integer result = namedParameterJdbcTemplate.queryForObject(CommentQueries.COUNT_NUMBER_OF_COMM_FOR_MOVIE,parameter, Integer.class );
        return result;
    }

}
