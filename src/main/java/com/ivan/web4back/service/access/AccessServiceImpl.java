package com.ivan.web4back.service.access;

import com.ivan.web4back.model.access.AccessEntity;
import com.ivan.web4back.model.access.AccessRepository;
import com.ivan.web4back.model.access.AuthProvider;
import com.ivan.web4back.model.access.Authority;
import com.ivan.web4back.model.account.AccountEntity;
import com.ivan.web4back.utils.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccessServiceImpl implements AccessService {
    private final AccessRepository repository;

    @Override
    public AccessEntity findByUsername(String username) throws UserNotFoundException {
        var user = repository.findByUsername(username);
        return user.orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    @Transactional
    public AccessEntity createOauthAccess(String username, AuthProvider authProvider, AccountEntity account, List<Authority> authorities) {
        if (authProvider == AuthProvider.LOCAL) {
            throw new IllegalArgumentException("No local auth provider");
        }
        var access = new AccessEntity(null, username, null, authProvider, true, account, authorities);
        var accessOptional = repository.findByUsername(access.getUsername());
        if (accessOptional.isPresent()) {
            throw new IllegalArgumentException("Access with username = " + access.getUsername() + " already exist");
        }

        return repository.save(access);
    }
}
