package io.javabrains.moviesplitMovieservice.models;

import lombok.*;

@EqualsAndHashCode
@Builder
@NoArgsConstructor
@Data
public class Chunck {
    public String id;

    private String videoId;
    private String videoChunck;

    public Chunck(String id,String videoId, String videoChunck) {
        this.id=id;
        this.videoId = videoId;
        this.videoChunck = videoChunck;
    }

    @Override
    public String toString() {
        return String.format(
                "{videoId=%s, videoChunck=%s}", videoId, videoChunck);
    }
}