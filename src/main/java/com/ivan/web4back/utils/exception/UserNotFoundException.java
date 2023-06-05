package com.ivan.web4back.utils.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("User (username='" + username + "' not found");
    }
}
