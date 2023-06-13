package com.ivan.web4back.service.auth;

import com.ivan.web4back.api.http.v1.auth.dto.BasicRegistrationRequest;
import com.ivan.web4back.utils.exception.UsernameAlreadyExistException;

public interface AuthService {
    void register(BasicRegistrationRequest registrationRequest) throws UsernameAlreadyExistException;
}
