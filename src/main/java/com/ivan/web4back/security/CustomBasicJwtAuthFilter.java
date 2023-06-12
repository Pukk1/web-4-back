package com.ivan.web4back.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class CustomBasicJwtAuthFilter extends BasicAuthenticationFilter {
    private final TokenProvider tokenProvider;

    public CustomBasicJwtAuthFilter(AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        super(authenticationManager);
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        var token = tokenProvider.createToken(authResult.getName());
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
