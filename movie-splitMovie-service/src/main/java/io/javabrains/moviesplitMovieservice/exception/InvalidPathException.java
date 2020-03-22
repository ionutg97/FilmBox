package io.javabrains.moviesplitMovieservice.exception;

public class InvalidPathException extends RuntimeException {
    public InvalidPathException() {
        super();
    }

    public InvalidPathException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPathException(String message) {
        super(message);
    }

    public InvalidPathException(Throwable cause) {
        super(cause);
    }
}
