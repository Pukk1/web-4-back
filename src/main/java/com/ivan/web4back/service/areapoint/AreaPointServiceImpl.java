package com.ivan.web4back.service.areapoint;

import com.ivan.web4back.api.http.v1.areapoint.dto.AreaPointRequest;
import com.ivan.web4back.model.areapoint.AreaPointEntity;
import com.ivan.web4back.model.areapoint.AreaPointRepository;
import com.ivan.web4back.service.access.AccessService;
import com.ivan.web4back.utils.exception.UserNotFoundException;
import com.ivan.web4back.utils.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AreaPointServiceImpl implements AreaPointService {
    private final AreaPointRepository repository;
    private final AccessService accessService;
    private final AuthenticationFacade authentication;

    @Override
    public AreaPointEntity create(AreaPointEntity entity) {
        return repository.save(entity);
    }

    @Override
    public AreaPointEntity create(AreaPointRequest request) throws UserNotFoundException {
        var username = authentication.getAuthentication().getName();
        var owner = accessService.findByUsername(username);
        var entity = request.toNewEntity(owner.getAccount());
        return create(entity);
    }
}
