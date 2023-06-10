package com.ivan.web4back.model.access;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    USER_ROLE;

    @Override
    public String getAuthority() {
        return name();
    }
}
