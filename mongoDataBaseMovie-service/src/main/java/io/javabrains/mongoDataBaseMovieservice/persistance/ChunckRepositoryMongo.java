package io.javabrains.mongoDataBaseMovieservice.persistance;

import io.javabrains.mongoDataBaseMovieservice.dto.MovieIdChunckDTO;
import io.javabrains.mongoDataBaseMovieservice.models.Chunck;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChunckRepositoryMongo extends MongoRepository<Chunck, String> {

    public Optional<Chunck> findById(String videoId);

    @Query(value="{ 'videoId' : ?0 }", fields="{ 'videoChunck' : 0, 'videoId' : 0, '_class' : 0}")
    public Optional<List<MovieIdChunckDTO>> findByVideoId(String videoId);
}
