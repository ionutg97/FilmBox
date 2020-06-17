package io.javabrains.dataBaseMovie.models.replication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RestTemplateSubject {
    String urlName;

    MovieReplication movieReplication;
}

