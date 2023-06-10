package com.ivan.web4back.api.http.v1.areapoint.dto;

import com.ivan.web4back.model.account.AccountEntity;
import com.ivan.web4back.model.areapoint.AreaPointEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AreaPointRequest {
    private final Double x;
    private final Double y;
    private final Double r;
    private final Boolean hit;
    private final LocalDateTime dateTime;

    public AreaPointEntity toEntity(Long id, AccountEntity owner) {
        return new AreaPointEntity(id, this.x, this.y, this.r, this.hit, this.dateTime, owner);
    }

    public AreaPointEntity toNewEntity(AccountEntity owner) {
        return toEntity(0L, owner);
    }
}
