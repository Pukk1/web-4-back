package com.ivan.web4back.service.areapoint;

import com.ivan.web4back.api.http.v1.areapoint.dto.AreaPointRequest;
import com.ivan.web4back.model.access.AccessEntity;
import com.ivan.web4back.model.areapoint.AreaPointEntity;
import com.ivan.web4back.model.areapoint.AreaPointRepository;
import com.ivan.web4back.service.access.AccessService;
import com.ivan.web4back.utils.AreaPointComparator;
import com.ivan.web4back.utils.auth.AuthenticationFacade;
import com.ivan.web4back.utils.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public List<AreaPointEntity> getAllForAccessByUsername(String username) throws UsernameNotFoundException {
        AccessEntity access;
        try {
            access = accessService.findByUsername(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(username, e);
        }
        var account = access.getAccount();
        var areaPoints = account.getAreaPoints();
        areaPoints.sort(new AreaPointComparator());
        return areaPoints;
    }

    @Override
    @Transactional
    public void deleteAllForAccessByUsername(String username) throws UsernameNotFoundException {
        AccessEntity access;
        try {
            access = accessService.findByUsername(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(username, e);
        }
        repository.deleteAllByOwnerId(access.getAccount().getId());
    }

    @Override
    public AreaPointEntity create(AreaPointRequest request) throws UsernameNotFoundException {
        var username = authentication.getAuthentication().getName();
        AccessEntity owner;
        try {
            owner = accessService.findByUsername(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(username, e);
        }
        var entity = request.toNewEntity(owner.getAccount(), checkArea(request));
        return create(entity);
    }
}
