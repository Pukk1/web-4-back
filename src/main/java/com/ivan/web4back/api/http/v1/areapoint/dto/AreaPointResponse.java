package com.ivan.web4back.api.http.v1.areapoint.dto;

import com.ivan.web4back.model.areapoint.AreaPointEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AreaPointResponse {
    private final Double x;
    private final Double y;
    private final Double r;
    private final Boolean hit;
    private final LocalDateTime dateTime;

    public static AreaPointResponse toResponse(AreaPointEntity entity) {
        return new AreaPointResponse(entity.getX(), entity.getY(), entity.getR(), entity.getHit(), entity.getDateTime());
    }
}
