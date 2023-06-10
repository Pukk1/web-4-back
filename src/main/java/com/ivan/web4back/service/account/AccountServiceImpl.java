package com.ivan.web4back.service.account;

import com.ivan.web4back.model.account.AccountEntity;
import com.ivan.web4back.model.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public AccountEntity createAccount(String name) {
        var newAccount = new AccountEntity(null, name, List.of(), Set.of());
        return accountRepository.save(newAccount);
    }
}
