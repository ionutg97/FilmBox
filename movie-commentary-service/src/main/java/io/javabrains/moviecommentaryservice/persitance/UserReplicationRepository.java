package io.javabrains.moviecommentaryservice.persitance;

import io.javabrains.moviecommentaryservice.models.User;
import io.javabrains.moviecommentaryservice.persitance.queries.UserReplicationQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository
public class UserReplicationRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserReplicationRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public User saveUserReplication(User user) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", user.getId());
        int result = namedParameterJdbcTemplate.update(UserReplicationQueries.INSERT_USER, parameters);
        return user;
    }

    public int deleteUserReplication(Long id) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        int result = namedParameterJdbcTemplate.update(UserReplicationQueries.DELETE_USER, parameters);
        return result;
    }
}
