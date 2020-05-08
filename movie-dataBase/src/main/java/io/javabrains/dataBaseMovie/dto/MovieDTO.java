package io.javabrains.dataBaseMovie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieDTO {
    private Long id;

    private String fileName;

    private Integer numberOfFiles;

    private Long totalSizeFile;

    private String videoId;

    private Long idUser;

}
