package com.mishalp789.auth_service.controller;

import com.mishalp789.auth_service.dto.AuthResponse;
import com.mishalp789.auth_service.dto.LoginRequest;
import com.mishalp789.auth_service.dto.RegisterRequest;
import com.mishalp789.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(
            @Valid @RequestBody RegisterRequest request
            ){
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request
            ){
        return authService.login(request);
    }
}
