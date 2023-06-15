package com.ivan.web4back.service.areapoint;

import com.ivan.web4back.api.http.v1.areapoint.dto.AreaPointRequest;
import com.ivan.web4back.model.areapoint.AreaPointEntity;
import com.ivan.web4back.model.areapoint.AreaPointRepository;
import com.ivan.web4back.service.access.AccessService;
import com.ivan.web4back.utils.AreaPointComparator;
import com.ivan.web4back.utils.auth.AuthenticationFacade;
import com.ivan.web4back.utils.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ivan.web4back.utils.AreaChecker.checkArea;

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
    @Transactional
    public List<AreaPointEntity> getAllForAccessByUsername(String username) throws UserNotFoundException {
        var access = accessService.findByUsername(username);
        var account = access.getAccount();
        var areaPoints = account.getAreaPoints();
        areaPoints.sort(new AreaPointComparator());
        return areaPoints;
    }

    @Override
    public AreaPointEntity create(AreaPointRequest request) throws UserNotFoundException {
        var username = authentication.getAuthentication().getName();
        var owner = accessService.findByUsername(username);
        var entity = request.toNewEntity(owner.getAccount(), checkArea(request));
        return create(entity);
    }
}
