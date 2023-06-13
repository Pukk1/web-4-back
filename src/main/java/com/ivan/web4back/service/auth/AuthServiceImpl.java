package com.ivan.web4back.service.auth;

import com.ivan.web4back.api.http.v1.auth.dto.BasicRegistrationRequest;
import com.ivan.web4back.model.access.Authority;
import com.ivan.web4back.service.access.AccessService;
import com.ivan.web4back.service.account.AccountService;
import com.ivan.web4back.utils.exception.UsernameAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AccountService accountService;
    private final AccessService accessService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(BasicRegistrationRequest registrationRequest) throws UsernameAlreadyExistException {
        var username = registrationRequest.getUsername();
        var password = registrationRequest.getPassword();
        var accountName = registrationRequest.getAccountName();

        password = passwordEncoder.encode(password);

        var account = accountService.createAccount(accountName);
        accessService.createBasicAccess(username, password, account, List.of(Authority.USER_ROLE));
    }
}
