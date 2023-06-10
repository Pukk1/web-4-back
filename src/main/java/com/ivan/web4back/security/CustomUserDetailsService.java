package com.ivan.web4back.security;

import com.ivan.web4back.model.access.AccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccessRepository accessRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var accessOptional = accessRepository.findByUsername(username);
        if (accessOptional.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return UserPrincipal.create(accessOptional.get());
    }
}
