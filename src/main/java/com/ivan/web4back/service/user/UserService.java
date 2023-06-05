package com.ivan.web4back.service.user;

import com.ivan.web4back.model.user.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> findByUsername(String username);

    void processOAuthPostLogin(String username);
}
