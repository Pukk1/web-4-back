package com.ivan.web4back.security.oauth2.user;

import com.ivan.web4back.model.access.AuthProvider;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getUsername() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return AuthProvider.GOOGLE.name() + attributes.get("name");
    }

    @Override
    public AuthProvider getProvider() {
        return AuthProvider.GOOGLE;
    }
}
