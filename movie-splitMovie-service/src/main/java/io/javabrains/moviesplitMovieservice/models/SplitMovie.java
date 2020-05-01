package io.javabrains.moviesplitMovieservice.models;

import lombok.*;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SplitMovie {

    private String videoId;

    private String fileName;

    private Integer numberOfFiles;

    private Long totalSizeFile;

    private Long chunckSize;

    private List<Chunck> listOfChunks;

    private Long idUser;

    public void incrementNumberOfFiles() {
        numberOfFiles++;
    }

    public void incrementTotalSize(Long currentSize) {
        this.totalSizeFile += currentSize;
    }

    public void generateVideoIdStoredInMongoDB() {
        UUID uuid = UUID.randomUUID();
        this.videoId = uuid.toString();
    }

}
