package io.javabrains.movieinfoservice.models.replication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RestTemplateSubject {
    String urlName;

    UserReplication userReplication;
}
