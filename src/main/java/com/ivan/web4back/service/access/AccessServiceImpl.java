package com.ivan.web4back.service.access;

import com.ivan.web4back.model.access.AccessEntity;
import com.ivan.web4back.model.access.AccessRepository;
import com.ivan.web4back.model.access.AuthProvider;
import com.ivan.web4back.model.access.Authority;
import com.ivan.web4back.model.account.AccountEntity;
import com.ivan.web4back.utils.exception.UserNotFoundException;
import com.ivan.web4back.utils.exception.UsernameAlreadyExistException;
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
    public AccessEntity createOauthAccess(String username, AuthProvider authProvider, AccountEntity account, List<Authority> authorities) throws UsernameAlreadyExistException {
        if (authProvider == AuthProvider.LOCAL) {
            throw new IllegalArgumentException("No local auth provider");
        }

        var access = new AccessEntity(null, username, null, authProvider, true, account, authorities);
        if (repository.findByUsername(access.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistException(access.getUsername());
        }

        return repository.save(access);
    }

    @Override
    @Transactional
    public AccessEntity createBasicAccess(String username, String password, AccountEntity account, List<Authority> authorities) throws UsernameAlreadyExistException {
        AccessEntity access = new AccessEntity(null, username, password, AuthProvider.LOCAL, true, account, authorities);

        if (repository.findByUsername(access.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistException(access.getUsername());
        }

        return repository.save(access);
    }
}
