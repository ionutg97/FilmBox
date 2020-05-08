package io.javabrains.dataBaseMovie.repository.mapper;

import io.javabrains.dataBaseMovie.dto.MovieDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieDTORowMapper implements RowMapper<MovieDTO> {

    @Override
    public MovieDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId(resultSet.getLong("id"));
        movieDTO.setFileName(resultSet.getString("file_name"));
        movieDTO.setNumberOfFiles(resultSet.getInt("number_chunks"));
        movieDTO.setTotalSizeFile(resultSet.getLong("total_size"));
        movieDTO.setVideoId(resultSet.getString("video_id"));
        movieDTO.setIdUser(resultSet.getLong("id_user"));

        return movieDTO;
    }
}
