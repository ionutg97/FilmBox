package io.javabrains.mongoDataBaseMovieservice.service;

import io.javabrains.mongoDataBaseMovieservice.dto.ListChunckDTO;
import io.javabrains.mongoDataBaseMovieservice.models.Chunck;
import io.javabrains.mongoDataBaseMovieservice.persistance.ChunckRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ListIterator;

@Service
public class PersistanceMongoService {

    @Autowired
    ChunckRepositoryMongo chunckRepositoryMongo;

    public ListChunckDTO saveChuncks(ListChunckDTO chuncks) {
        ListIterator<Chunck> chuncksListIterator = chuncks.getListOfChuncks().listIterator();
        while (chuncksListIterator.hasNext()) {
            Chunck itemSaved = chunckRepositoryMongo.save(chuncksListIterator.next());
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
}

