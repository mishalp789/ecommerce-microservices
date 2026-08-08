package com.mishalp789.auth_service.service;

import com.mishalp789.auth_service.dto.AuthResponse;
import com.mishalp789.auth_service.dto.LoginRequest;
import com.mishalp789.auth_service.dto.RegisterRequest;
import com.mishalp789.auth_service.entity.User;
import com.mishalp789.auth_service.exception.UserAlreadyExistsException;
import com.mishalp789.auth_service.exception.UserNotFoundException;
import com.mishalp789.auth_service.mapper.UserMapper;
import com.mishalp789.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException(request.getEmail());
        }
        User user = userMapper.toEntity(request);
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new UserNotFoundException(request.getEmail()));
        return userMapper.toResponse(user);
    }
}
