package com.ivan.web4back.api.http.v1.auth.dto;

import lombok.Data;

@Data
public class BasicRegistrationRequest {
    private final String accountName;
    private final String username;
    private final String password;
}
