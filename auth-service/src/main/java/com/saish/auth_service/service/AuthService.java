package com.saish.auth_service.service;

import com.saish.auth_service.dto.LoginRequest;
import com.saish.auth_service.dto.LoginResponse;
import com.saish.auth_service.dto.RegisterRequest;
import com.saish.auth_service.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}