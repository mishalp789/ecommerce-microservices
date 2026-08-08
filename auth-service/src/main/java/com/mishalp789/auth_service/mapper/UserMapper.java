package com.mishalp789.auth_service.mapper;

import com.mishalp789.auth_service.dto.AuthResponse;
import com.mishalp789.auth_service.dto.RegisterRequest;
import com.mishalp789.auth_service.entity.Role;
import com.mishalp789.auth_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(RegisterRequest request){

        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.ROLE_USER)
                .build();

    }

    public AuthResponse toResponse(User user){

        return AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .token(null)
                .build();

    }

}