package com.ivan.web4back.security.oauth2;

import com.ivan.web4back.model.access.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class UserOAuth2Principal implements OAuth2User {
    private final Map<String, Object> attributes;
    private final List<Authority> authorities;
    private final String name;
    private final String username;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return username;
    }
}
