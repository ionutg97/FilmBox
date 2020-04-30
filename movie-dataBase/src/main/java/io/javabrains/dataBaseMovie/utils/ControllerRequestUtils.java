package io.javabrains.dataBaseMovie.utils;

import io.javabrains.dataBaseMovie.models.replication.MovieReplication;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ControllerRequestUtils {

    public static HttpEntity<String> createRequest(MovieReplication movieReplication) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = ControllerRequestUtils.createJSON(movieReplication);
        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);
        return request;
    }

    public static JSONObject createJSON(MovieReplication movieReplication) {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("id", movieReplication.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
