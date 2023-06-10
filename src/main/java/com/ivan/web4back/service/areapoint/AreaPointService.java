package com.ivan.web4back.service.areapoint;

import com.ivan.web4back.api.http.v1.areapoint.dto.AreaPointRequest;
import com.ivan.web4back.model.areapoint.AreaPointEntity;
import com.ivan.web4back.utils.exception.UserNotFoundException;

public interface AreaPointService {
    AreaPointEntity create(AreaPointRequest request) throws UserNotFoundException;
    AreaPointEntity create(AreaPointEntity entity);
}
