package com.ivan.web4back.service.access;

import com.ivan.web4back.model.access.AccessEntity;
import com.ivan.web4back.model.access.AuthProvider;
import com.ivan.web4back.model.access.Authority;
import com.ivan.web4back.model.account.AccountEntity;
import com.ivan.web4back.utils.exception.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccessService {
    AccessEntity findByUsername(String username) throws UserNotFoundException;

    @Transactional
    AccessEntity createOauthAccess(String username, AuthProvider authProvider, AccountEntity account, List<Authority> authorities);
}
