package com.mishalp789.auth_service.service;

import com.mishalp789.auth_service.dto.AuthResponse;
import com.mishalp789.auth_service.dto.LoginRequest;
import com.mishalp789.auth_service.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}