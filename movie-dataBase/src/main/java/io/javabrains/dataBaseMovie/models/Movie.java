package io.javabrains.dataBaseMovie.models;

import lombok.*;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Movie {
    private Long id;

    private String fileName;

    private Integer numberOfFiles;

    private Long totalSizeFile;

    private Long chunckSize;

    private Long idBlobStorage;

    private String videoId;

    private Long idUser;
}
