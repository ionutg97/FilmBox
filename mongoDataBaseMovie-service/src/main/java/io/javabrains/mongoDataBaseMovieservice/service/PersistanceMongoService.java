package io.javabrains.mongoDataBaseMovieservice.service;

import io.javabrains.mongoDataBaseMovieservice.dto.ListChunckDTO;
import io.javabrains.mongoDataBaseMovieservice.dto.MovieIdChunckDTO;
import io.javabrains.mongoDataBaseMovieservice.exception.ResourceNotFoundException;
import io.javabrains.mongoDataBaseMovieservice.models.Chunck;
import io.javabrains.mongoDataBaseMovieservice.persistance.ChunckRepositoryMongo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Service
public class PersistanceMongoService {

    @Autowired
    ChunckRepositoryMongo chunckRepositoryMongo;

    public ListChunckDTO saveChuncks(ListChunckDTO chuncks) {
        ListIterator<Chunck> chuncksListIterator = chuncks.getListOfChuncks().listIterator();
        while (chuncksListIterator.hasNext()) {
            Chunck itemSaved = chunckRepositoryMongo.save(chuncksListIterator.next());
            itemSaved.setId(itemSaved.get_id());
            chuncksListIterator.set(itemSaved);

        }
        return chuncks;
    }

    public Chunck getChuncks(String id) {
       // ListChunckDTO listChunckDTO = new ListChunckDTO();
       // listChunckDTO.setListOfChuncks(chunckRepositoryMongo.findByVideoId(id));
       // return listChunckDTO;
        Chunck chunck=new Chunck();
        chunck=(chunckRepositoryMongo.findById(id).get());
        return chunck;
    }

    public List<String> getListOfId(String videoId)
    {
        List<MovieIdChunckDTO> result= chunckRepositoryMongo.findByVideoId(videoId)
                .orElseThrow(() -> new ResourceNotFoundException(MovieIdChunckDTO.class.getSimpleName()));;

         return result.stream()
                 .map(x->x.getId())
                 .collect(Collectors.toList());
    }
}

