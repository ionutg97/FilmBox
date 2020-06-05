package io.javabrains.moviecommentaryservice.models;

import lombok.*;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class Comment {
    Long id;
    String content;
    Long idUser;
    String idMovie;
}
