package io.javabrains.moviesplitMovieservice.models;

import lombok.*;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SplitMovie {

    private String fileName;

    private Integer numberOfFiles;

    private Long totalSizeFile;

    private Long chunckSize;

    public void incrementNumberOfFiles()
    {
        numberOfFiles++;
    }

    public void incrementTotalSize(Long currentSize){
        this.totalSizeFile+=currentSize;
    }

}
