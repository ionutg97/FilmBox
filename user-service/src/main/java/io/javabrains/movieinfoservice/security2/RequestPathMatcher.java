package io.javabrains.movieinfoservice.security2;

import org.springframework.util.AntPathMatcher;

import java.util.List;

public class RequestPathMatcher extends AntPathMatcher {
    private List<String> unauthorizedPaths;

    public RequestPathMatcher(List<String> unauthorizedPaths) {
        this.unauthorizedPaths = unauthorizedPaths;
    }

    public boolean isRequestNonauthorized(String requestURI) {
        for (String path : unauthorizedPaths) {
            if (super.doMatch(path, requestURI, true, null))
                return true;
        }
        return false;
    }
}