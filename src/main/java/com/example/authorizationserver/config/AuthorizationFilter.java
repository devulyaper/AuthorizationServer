package com.example.authorizationserver.config;

import com.example.authorizationserver.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final List<String> ignoredEndpoints = new ArrayList<>();

    public AuthorizationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
        ignoredEndpoints.add("/login");
        ignoredEndpoints.add("/registration");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (isIgnoredEndpoint(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || authHeader.isBlank()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (!checkAuthorization(authHeader)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean checkAuthorization(String auth) {
        if (!auth.startsWith("Bearer ")) {
            return false;
        }

        String token = auth.substring(7);
        return tokenService.checkToken(token);
    }

    private boolean isIgnoredEndpoint(String requestURI) {
        return ignoredEndpoints.stream().anyMatch(requestURI::startsWith);
    }
}
