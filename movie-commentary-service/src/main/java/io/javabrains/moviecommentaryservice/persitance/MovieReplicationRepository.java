package io.javabrains.moviecommentaryservice.persitance;

import io.javabrains.moviecommentaryservice.models.Movie;
import io.javabrains.moviecommentaryservice.persitance.queries.MovieReplicationQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository
public class MovieReplicationRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MovieReplicationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Movie saveMovieReplication(Movie movie) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", movie.getId());
        int result = namedParameterJdbcTemplate.update(MovieReplicationQueries.INSERT_MOVIE, parameters);
        return movie;
    }

    public int deleteMovieReplication(Long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        int result = namedParameterJdbcTemplate.update(MovieReplicationQueries.DELETE_MOVIE, parameters);
        return result;
    }
}
