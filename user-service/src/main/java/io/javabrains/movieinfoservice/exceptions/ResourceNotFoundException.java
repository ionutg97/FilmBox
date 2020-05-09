package io.javabrains.movieinfoservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Exception exception) {
        super(exception);
    }

    public ResourceNotFoundException(String entityClassName, Long id) {
        super(entityClassName + " with id " + id + " does not exist!");
    }

    public ResourceNotFoundException(String entityClassName, String entityTitle) {
        super(entityClassName + " with " + entityTitle + " does not exist!");
    }
}