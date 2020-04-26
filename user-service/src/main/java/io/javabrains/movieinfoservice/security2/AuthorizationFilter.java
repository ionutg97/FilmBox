package io.javabrains.movieinfoservice.security2;

import com.google.gson.GsonBuilder;
import io.javabrains.movieinfoservice.exceptions.InvalidAuthenticationException;
import io.javabrains.movieinfoservice.models.TokenSubject;
import io.javabrains.movieinfoservice.models.User;
import io.javabrains.movieinfoservice.persistance.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Objects;

public class AuthorizationFilter extends GenericFilterBean {

    Long EXPIRATION_TIME = Long.valueOf(28800000);
    String SECRET = "45CB5FCC6611MAR6AB315A00F0C2F05A";
    String TOKEN_PREFIX = "Bearer";
    String HEADER = "Authorization";

    private UserRepository userRepository;
    private RequestPathMatcher requestPathMatcher;

    public AuthorizationFilter(UserRepository userRepository, RequestPathMatcher requestPathMatcher) {
        this.userRepository = userRepository;
        this.requestPathMatcher = requestPathMatcher;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!requestPathMatcher.isRequestNonauthorized(((HttpServletRequest) servletRequest).getRequestURI())) {
            Authentication authentication;
            try {
                authentication = getAuthentication((HttpServletRequest) servletRequest);
            } catch (InvalidAuthenticationException ex) {
                HttpServletResponse httpServletResponse = ((HttpServletResponse) servletResponse);
                httpServletResponse.setStatus(401);
                httpServletResponse.setHeader("Authorization", "expired");
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private TokenSubject getTokenSubjectFromJson(String json) {
        return new GsonBuilder().create().fromJson(json, TokenSubject.class);
    }


    public Authentication getAuthentication(HttpServletRequest request) throws UnsupportedEncodingException {
        String token = request.getHeader(HEADER);
        if (!Objects.isNull(token)) {
            TokenSubject tokenSubject = getTokenSubjectFromJson(
                    Jwts.parser()
                            .setSigningKey(SECRET)
                            .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                            .getBody()
                            .getSubject()
            );
            User user = userRepository.findByName(tokenSubject.getName()).orElseThrow(InvalidAuthenticationException::new);
            return new UsernamePasswordAuthenticationToken(tokenSubject, user.getPassword(),
                    Arrays.asList(new SimpleGrantedAuthority(user.getRole().toString())));
        }
        throw new InvalidAuthenticationException();
    }
}