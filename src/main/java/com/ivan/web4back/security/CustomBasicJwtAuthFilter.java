package com.ivan.web4back.security;

import com.ivan.web4back.model.account.AccountEntity;
import com.ivan.web4back.service.access.AccessService;
import com.ivan.web4back.utils.exception.UserNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class CustomBasicJwtAuthFilter extends BasicAuthenticationFilter {
    private final TokenProvider tokenProvider;
    private final AccessService accessService;

    public CustomBasicJwtAuthFilter(AuthenticationManager authenticationManager, TokenProvider tokenProvider, AccessService accessService) {
        super(authenticationManager);
        this.tokenProvider = tokenProvider;
        this.accessService = accessService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilterInternal(request, response, chain);
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        var username = authResult.getName();
        String accountName;
        try {
            accountName = accessService.findByUsername(authResult.getName()).getAccount().getName();
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        var token = tokenProvider.createToken(username, accountName);
        response.addHeader(HttpHeaders.AUTHORIZATION, /*"Bearer " + */token);
        response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        super.onUnsuccessfulAuthentication(request, response, failed);
    }
}
