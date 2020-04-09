package io.javabrains.mongoDataBaseMovieservice.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode
@Builder
@NoArgsConstructor
@Data
@Document
public class Chunck {

    @Id
    public ObjectId _id;
    private String videoId;
    private String videoChunck;
    private String id;

    public Chunck(ObjectId _id, String videoId, String videoChunck,String id) {
        this._id=_id;
        this.videoId = videoId;
        this.videoChunck = videoChunck;
        this.id=id;
    }

    public void setId(ObjectId _id)
    {
        this.id=_id.toString();
    }

    @Override
    public String toString() {
        return String.format(
                "Chunck[videoId=%s, videoChunck=%s]", videoId, videoChunck);
    }
}
