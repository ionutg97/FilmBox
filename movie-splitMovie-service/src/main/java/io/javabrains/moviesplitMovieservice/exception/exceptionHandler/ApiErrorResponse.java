package io.javabrains.moviesplitMovieservice.exception.exceptionHandler;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ApiErrorResponse {

    private String error;
    private int errorCode;
    private Date timestamp;
    private String message;
    private String path;

}


