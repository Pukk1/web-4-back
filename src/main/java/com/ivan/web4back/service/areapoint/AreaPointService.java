package com.ivan.web4back.service.areapoint;

import com.ivan.web4back.api.http.v1.areapoint.dto.AreaPointRequest;
import com.ivan.web4back.model.areapoint.AreaPointEntity;

public interface AreaPointService {
    AreaPointEntity create(AreaPointRequest request);
    AreaPointEntity create(AreaPointEntity entity);
}
