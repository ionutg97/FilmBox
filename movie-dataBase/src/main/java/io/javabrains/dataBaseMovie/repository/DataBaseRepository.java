package io.javabrains.dataBaseMovie.repository;

import io.javabrains.dataBaseMovie.models.Movie;
import io.javabrains.dataBaseMovie.repository.queries.MovieQueries;
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

@Repository
@Slf4j
public class DataBaseRepository {

    private KeyHolder keyHolder;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DataBaseRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    void init() {
        keyHolder=new GeneratedKeyHolder();
    }
    public Movie save(Movie movie) {

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("file_name", movie.getFileName())
                .addValue("number_chunks", movie.getNumberOfFiles())
                .addValue("total_size", movie.getTotalSizeFile())
                .addValue("chunk_size", movie.getChunckSize())
                .addValue("id_blob_storage", movie.getIdBlobStorage())
                .addValue("video_id",movie.getVideoId());

        int result = namedParameterJdbcTemplate.update(MovieQueries.INSERT_MOVIE, parameters, keyHolder);
        List<Map<String, Object>> keyList = keyHolder.getKeyList();
        Long id = (Long) keyList.get(0).get("id");
        movie.setId(id);
        log.info("Saving new movie in movies data base with id: {}", movie.getId());

        return movie;

    }
}

