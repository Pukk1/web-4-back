package com.ivan.web4back.security.oauth2.user;

import com.ivan.web4back.model.access.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String providerName, Map<String, Object> attributes) {
        if (providerName.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (providerName.equalsIgnoreCase(AuthProvider.VK.toString())) {
            //TODO
            throw new RuntimeException("TODO");
        } else {
            throw new IllegalArgumentException("Provider name = " + providerName + " not found");
        }
    }
}
