package io.javabrains.moviecommentaryservice.basicSecurity;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenSubject {
    private long id;
    private String role;
    private String name;
}