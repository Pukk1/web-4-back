package com.ivan.web4back.utils.exception;

import javax.naming.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException(String username) {
        super("User (username='" + username + "') not found");
    }
}
