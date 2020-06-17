package io.javabrains.movieinfoservice.security2;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

//@PropertySource("classpath:security.properties")
@Component
public class SecurityConstants {

    private static long EXPIRATION_TIME;
    private static String SECRET;
    private static String TOKEN_PREFIX;
    private static String HEADER;


    private static String[] nonauthorizedPaths = new String[]{
            "/user/new_user",
            "another/path"
    };

    public static List<String> getNonauthorizedPaths() {
        return Arrays.asList(nonauthorizedPaths);
    }

}