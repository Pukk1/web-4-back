package com.ivan.web4back.service.user;

import com.ivan.web4back.model.user.AuthProvider;
import com.ivan.web4back.model.user.UserEntity;
import com.ivan.web4back.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public UserEntity findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public void processOAuthPostLogin(String username) {
        UserEntity existUser = repository.findByUsername(username);

        if (existUser == null) {
            UserEntity newUser = new UserEntity();
            newUser.setUsername(username);
            newUser.setProvider(AuthProvider.GOOGLE);
            newUser.setEnabled(true);

            repository.save(newUser);
        }

    }
}
