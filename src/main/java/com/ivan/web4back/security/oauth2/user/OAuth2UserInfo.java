package com.ivan.web4back.security.oauth2.user;

import com.ivan.web4back.model.access.AuthProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class OAuth2UserInfo {
    protected final Map<String, Object> attributes;

    public abstract String getUsername();
    public abstract String getName();
    public abstract AuthProvider getProvider();
}
