package com.ivan.web4back.api.http.v1.areapoint.dto;

import com.ivan.web4back.model.areapoint.AreaPointEntity;
import com.ivan.web4back.model.user.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AreaPointRequest {
    private final Double x;
    private final Double y;
    private final Double r;
    private final Boolean hit;
    private final LocalDateTime dateTime;

    public AreaPointEntity toEntity(Long id, UserEntity owner) {
        return new AreaPointEntity(id, owner, this.x, this.y, this.r, this.hit, this.dateTime);
    }

    public AreaPointEntity toNewEntity(UserEntity owner) {
        return toEntity(0L, owner);
    }
}
