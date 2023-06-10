package com.ivan.web4back.security;

import com.ivan.web4back.model.access.AccessEntity;
import com.ivan.web4back.model.access.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
    private final String username;
    private final String password;
    private final Boolean enabled;
    private final List<Authority> grantedAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public static UserPrincipal create(AccessEntity access) {
        return new UserPrincipal(
                access.getUsername(),
                access.getPassword(),
                access.getEnabled(),
                access.getAuthorities()
        );
    }
}
