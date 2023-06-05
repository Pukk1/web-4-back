package com.ivan.web4back.service.areapoint;

import com.ivan.web4back.api.http.v1.areapoint.dto.AreaPointRequest;
import com.ivan.web4back.model.areapoint.AreaPointEntity;
import com.ivan.web4back.model.areapoint.AreaPointRepository;
import com.ivan.web4back.service.user.UserService;
import com.ivan.web4back.utils.exception.UserNotFoundException;
import com.ivan.web4back.utils.exception.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AreaPointServiceImpl implements AreaPointService {
    private final AreaPointRepository repository;
    private final UserService userService;
    private final AuthenticationFacade authentication;

    @Override
    public AreaPointEntity create(AreaPointEntity entity) {
        return repository.save(entity);
    }

    @Override
    public AreaPointEntity create(AreaPointRequest request) {
        var username = authentication.getAuthentication().getName();
        var owner = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        var entity = request.toNewEntity(owner);
        return create(entity);
    }
}
