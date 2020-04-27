package io.javabrains.moviecommentaryservice.security;

import com.google.gson.GsonBuilder;

public class Utils {

    public static final String SECRET = "45CB5FCC6611MAR6AB315A00F0C2F05A";
    public static final String TOKEN_PREFIX = "Bearer";

    public static  TokenSubject getTokenSubjectFromJson(String json) {
        return new GsonBuilder().create().fromJson(json, TokenSubject.class);
    }

}
