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
    private final LocalDateTime dateTime;

    public AreaPointEntity toEntity(Long id, AccountEntity owner, boolean hit) {
        return new AreaPointEntity(id, this.x, this.y, this.r, hit, this.dateTime, owner);
    }

    public AreaPointEntity toNewEntity(AccountEntity owner, boolean hit) {
        return toEntity(0L, owner, hit);
    }
}
