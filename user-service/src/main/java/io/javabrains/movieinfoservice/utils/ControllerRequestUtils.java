package io.javabrains.movieinfoservice.utils;

import io.javabrains.movieinfoservice.models.replication.UserReplication;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ControllerRequestUtils {

    public static HttpEntity<String> createRequest(UserReplication userReplication) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = ControllerRequestUtils.createJSON(userReplication);
        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);
        return request;
    }

    public static JSONObject createJSON(UserReplication userReplication) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", userReplication.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
