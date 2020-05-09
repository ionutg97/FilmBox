package io.javabrains.movieinfoservice.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Objects;

public class ControllerResponseUtils {
    private static HttpHeaders responseHeaders;
    private static HttpHeaders requestHeaders;

    public static HttpHeaders getResponseHeaders() {
        if (Objects.isNull(responseHeaders)) {
            responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        }
        return responseHeaders;
    }

}