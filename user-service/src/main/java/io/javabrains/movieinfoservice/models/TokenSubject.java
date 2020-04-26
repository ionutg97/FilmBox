package io.javabrains.movieinfoservice.models;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenSubject {
    private long id;
    private String role;
    private String name;
}