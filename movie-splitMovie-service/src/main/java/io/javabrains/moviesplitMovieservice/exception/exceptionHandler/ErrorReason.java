package io.javabrains.moviesplitMovieservice.exception.exceptionHandler;

import org.springframework.http.HttpStatus;

public enum ErrorReason {

    PATH_FILE_INVALID(HttpStatus.BAD_REQUEST),
    TECHNICAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

    private HttpStatus httpStatus;

    ErrorReason(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}