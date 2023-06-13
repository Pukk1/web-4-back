package com.ivan.web4back.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameAlreadyExistException extends AuthenticationException {
    public UsernameAlreadyExistException(String username) {
        super("User (username='" + username + "') already exist");
    }
}
