package io.javabrains.moviesplitMovieservice.dto;

import io.javabrains.moviesplitMovieservice.models.Chunck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ListOfChunckDTO {

    private List<Chunck> listOfChuncks;
}
