package com.ivan.web4back.api.http.v1.areapoint;

import com.ivan.web4back.api.http.v1.areapoint.dto.AreaPointRequest;
import com.ivan.web4back.api.http.v1.areapoint.dto.AreaPointResponse;
import com.ivan.web4back.service.areapoint.AreaPointService;
import com.ivan.web4back.utils.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/area-point")
public class AreaPointController {
    private final AreaPointService service;

    @PostMapping
    @Transactional
    public AreaPointResponse create(AreaPointRequest request) throws UserNotFoundException {
        var resEntity = service.create(request);
        return AreaPointResponse.toResponse(resEntity);
    }
}
