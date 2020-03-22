package io.javabrains.mongoDataBaseMovieservice.dto;

import io.javabrains.mongoDataBaseMovieservice.models.Chunck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListChunckDTO {

    private List<Chunck> listOfChuncks;
}
