package io.javabrains.moviecommentaryservice.models;

import lombok.*;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
    Long id;
    String content;
    Long idUser;
    Long idMovie;
}
