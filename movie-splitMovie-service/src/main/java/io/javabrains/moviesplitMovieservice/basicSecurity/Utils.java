package io.javabrains.moviesplitMovieservice.basicSecurity;

import com.google.gson.GsonBuilder;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

    public static final String SECRET = "45CB5FCC6611MAR6AB315A00F0C2F05A";
    public static final String TOKEN_PREFIX = "Bearer";

    public static TokenSubject getTokenSubjectFromJson(String json) {
        return new GsonBuilder().create().fromJson(json, TokenSubject.class);
    }

    public static TokenSubject validateRequestUsingJWT(String token) {
        TokenSubject tokenSubject = null;
        try {
            if (token != null) {
                tokenSubject = Utils.getTokenSubjectFromJson(
                        Jwts.parser()
                                .setSigningKey(Utils.SECRET)
                                .parseClaimsJws(token.replace(Utils.TOKEN_PREFIX, ""))
                                .getBody()
                                .getSubject()
                );
            }
        } catch (ExpiredJwtException e) {
            log.error("Token expired ");
            return null;
        } catch (SignatureException e) {
            log.error("Signature exception " + Jwts.class.getName() + " " + token);
            return null;
        } catch (Exception e) {
            log.error(" Some other exception in JWT parsing ");
            return null;
        }
        return tokenSubject;
    }
}
