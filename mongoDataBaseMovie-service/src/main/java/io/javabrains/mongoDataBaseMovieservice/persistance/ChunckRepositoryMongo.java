package io.javabrains.mongoDataBaseMovieservice.persistance;

import io.javabrains.mongoDataBaseMovieservice.models.Chunck;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChunckRepositoryMongo extends MongoRepository<Chunck, String> {

    public Optional<Chunck> findById(String videoId);
}
