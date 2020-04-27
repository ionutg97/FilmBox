package io.javabrains.movieinfoservice.security2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javabrains.movieinfoservice.models.TokenSubject;
import io.javabrains.movieinfoservice.models.User;
import io.javabrains.movieinfoservice.models.UserCredentials;
import io.javabrains.movieinfoservice.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

@Service
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    Long EXPIRATION_TIME = Long.valueOf(28800000);
    String SECRET = "45CB5FCC6611MAR6AB315A00F0C2F05A";
    String TOKEN_PREFIX = "Bearer";
    String HEADER = "Authorization";

    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super("/api/login");
        super.setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) {
                TokenSubject tokenSubject = getTokenFromAuthentication(authentication);

                String JWT = Jwts.builder()
                        .setSubject(getJsonFromTokenSubject(tokenSubject))
                        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                        .signWith(SignatureAlgorithm.HS512, SECRET)
                        .compact();

                response.addHeader("Access-Control-Allow-Headers", HEADER);
                response.addHeader("Access-Control-Expose-Headers", HEADER);
                response.addHeader(HEADER, TOKEN_PREFIX.concat(JWT));

                response.setStatus(HttpStatus.OK.value());
            }
        });
    }

    public TokenSubject getTokenFromAuthentication(Authentication authentication) {
        String usernameInserted = authentication.getName();
        User user = userService.getUserByName(usernameInserted);

        return TokenSubject.builder()
                .name(user.getName())
                .id(user.getId())
                .role(user.getRole())
                .build();
    }

    public String getJsonFromTokenSubject(TokenSubject tokenSubject) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(tokenSubject);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        UserCredentials userCredentials = getUserCredentialsFromRequest(httpServletRequest);
        User user = getUserFromUsersCredentials(userCredentials);

        Authentication authentication = null;

        try {
            authentication = getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userCredentials.getUsername(),
                            userCredentials.getPassword(),
                            Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()))
                    ));

        } catch (InternalAuthenticationServiceException exception) {
            httpServletResponse.setHeader("Content-type", "application/json");
            httpServletResponse.getWriter().write(exception.getMessage());
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        return authentication;
    }

    public UserCredentials getUserCredentialsFromRequest(HttpServletRequest httpServletRequest) throws IOException {
        BufferedReader reader = httpServletRequest.getReader();
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(buffer.toString(), UserCredentials.class);
    }

    public User getUserFromUsersCredentials(UserCredentials userCredentials) throws IOException {
        return userService.getUserByName(userCredentials.getUsername());
    }

}