package com.ivan.web4back.api.http.v1.auth;

import com.ivan.web4back.api.http.v1.auth.dto.BasicRegistrationRequest;
import com.ivan.web4back.service.auth.AuthService;
import com.ivan.web4back.utils.exception.UsernameAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration/local")
    public void register(@RequestBody BasicRegistrationRequest basicRegistrationRequest) throws UsernameAlreadyExistException {
        authService.register(basicRegistrationRequest);
    }

    @PostMapping("/login/basic")
    public ResponseEntity<?> loginStub() {
        return ResponseEntity.ok().build();
    }
}
