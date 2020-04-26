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
            "/api/activation/**",
            "/api/activation*",
            "/api/users/resetPassword/*",
            "/api/users/request_password_reset",
            "/api/users/verify/is_password_reset/*"
    };

    public static List<String> getNonauthorizedPaths() {
        return Arrays.asList(nonauthorizedPaths);
    }

}