package com.ivan.web4back.security.oauth2.user;

import com.ivan.web4back.model.access.AuthProvider;

import java.util.Map;

public class VkOAuth2UserInfo extends OAuth2UserInfo {
    public VkOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getUsername() {
        return getProvider().name() + attributes.get("id");
    }

    @Override
    public String getName() {
        return attributes.get("first_name") + " " + attributes.get("last_name");
    }

    @Override
    public AuthProvider getProvider() {
        return AuthProvider.VK;
    }
}
