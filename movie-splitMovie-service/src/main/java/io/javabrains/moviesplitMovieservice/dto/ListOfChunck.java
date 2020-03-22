package io.javabrains.moviesplitMovieservice.dto;

import io.javabrains.moviesplitMovieservice.models.Chunck;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ListOfChunck {

    private List<Chunck> listOfChuncks;
}
