package com.ivan.web4back.service.areapoint;

import com.ivan.web4back.api.http.v1.areapoint.dto.AreaPointRequest;
import com.ivan.web4back.model.areapoint.AreaPointEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface AreaPointService {
    AreaPointEntity create(AreaPointRequest request) throws UsernameNotFoundException;
    AreaPointEntity create(AreaPointEntity entity);
    List<AreaPointEntity> getAllForAccessByUsername(String username) throws UsernameNotFoundException;
    void deleteAllForAccessByUsername(String username) throws UsernameNotFoundException;
}
