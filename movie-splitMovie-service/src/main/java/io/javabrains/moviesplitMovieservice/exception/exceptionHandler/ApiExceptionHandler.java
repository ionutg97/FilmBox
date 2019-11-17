package io.javabrains.moviesplitMovieservice.exception.exceptionHandler;

import io.javabrains.moviesplitMovieservice.exception.InvalidPathException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.io.FileNotFoundException;
import java.util.Date;


@ControllerAdvice
public class ApiExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleFileNotFoundException(Exception ex, WebRequest request) {
        return handleInternalException(ex, request, ErrorReason.PATH_FILE_INVALID);
    }

    @ExceptionHandler(InvalidPathException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidPathException(Exception ex, WebRequest request) {
        return handleInternalException(ex, request, ErrorReason.PATH_FILE_INVALID);
    }

    private ResponseEntity<ApiErrorResponse> handleInternalException(Exception ex, WebRequest request, ErrorReason errorReason) {
        LOGGER.info("Caught {} while processing request [message={}]" + ex.getClass().getSimpleName() + ex.getMessage());
        ApiErrorResponse response = ApiErrorResponse
                .builder()
                .timestamp(new Date())
                .error(errorReason.name())
                .errorCode(errorReason.getHttpStatus().value())
                .message(ex.getMessage())
                .path(getPath(request))
                .build();
        return new ResponseEntity<>(response, errorReason.getHttpStatus());
    }

    private String getPath(WebRequest request) {
        String substring = ((ServletWebRequest) request).getRequest().getRequestURI().substring(request.getContextPath().length());
        return request.getContextPath();
    }


}