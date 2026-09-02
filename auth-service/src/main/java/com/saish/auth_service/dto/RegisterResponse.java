package com.saish.auth_service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponse {

    private Long id;
    private String username;
    private String email;
    private String role;
}