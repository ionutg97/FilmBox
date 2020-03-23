package io.javabrains.mongoDataBaseMovieservice.models;

import lombok.*;
import org.springframework.data.annotation.Id;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chunck {

    @Id
    public String id;
    private String videoId;
    private String videoChunck;

    public Chunck(String videoId, String videoChunck) {
        this.videoId = videoId;
        this.videoChunck = videoChunck;
    }

    @Override
    public String toString() {
        return String.format(
                "Chunck[videoId=%s, videoChunck=%s]", videoId, videoChunck);
    }
}
