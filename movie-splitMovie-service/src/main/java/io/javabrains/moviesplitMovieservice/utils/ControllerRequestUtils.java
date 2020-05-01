package io.javabrains.moviesplitMovieservice.utils;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ControllerRequestUtils {

    public static HttpEntity<String> createRequest(JSONObject jsonObject,String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization",token);

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);
        return request;
    }


}
