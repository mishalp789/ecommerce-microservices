package com.mishalp789.auth_service.service;

import com.mishalp789.auth_service.dto.AuthResponse;
import com.mishalp789.auth_service.dto.LoginRequest;
import com.mishalp789.auth_service.dto.RegisterRequest;
import com.mishalp789.auth_service.entity.User;
import com.mishalp789.auth_service.exception.UserAlreadyExistsException;
import com.mishalp789.auth_service.exception.UserNotFoundException;
import com.mishalp789.auth_service.mapper.UserMapper;
import com.mishalp789.auth_service.repository.UserRepository;
import com.mishalp789.auth_service.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl service;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;
    private AuthResponse authResponse;

    @BeforeEach
    void setup() {

        registerRequest = RegisterRequest.builder()
                .name("Mishal")
                .email("mishal@gmail.com")
                .password("password")
                .build();

        loginRequest = LoginRequest.builder()
                .email("mishal@gmail.com")
                .password("password")
                .build();

        user = User.builder()
                .id(1L)
                .name("Mishal")
                .email("mishal@gmail.com")
                .password("encodedPassword")
                .build();

        authResponse = AuthResponse.builder()
                .id(1L)
                .name("Mishal")
                .email("mishal@gmail.com")
                .build();
    }

    @Test
    void shouldRegisterUser() {

        when(userRepository.existsByEmail(registerRequest.getEmail()))
                .thenReturn(false);

        when(userMapper.toEntity(registerRequest))
                .thenReturn(user);

        when(passwordEncoder.encode(registerRequest.getPassword()))
                .thenReturn("encodedPassword");

        when(userRepository.save(user))
                .thenReturn(user);

        when(userMapper.toResponse(user))
                .thenReturn(authResponse);

        AuthResponse result = service.register(registerRequest);

        assertNotNull(result);
        assertEquals("mishal@gmail.com", result.getEmail());

        verify(userRepository).existsByEmail(registerRequest.getEmail());
        verify(passwordEncoder).encode(registerRequest.getPassword());
        verify(userRepository).save(user);
        verify(userMapper).toResponse(user);
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {

        when(userRepository.existsByEmail(registerRequest.getEmail()))
                .thenReturn(true);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> service.register(registerRequest)
        );

        verify(userRepository).existsByEmail(registerRequest.getEmail());
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldLoginSuccessfully() {

        when(userRepository.findByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword()))
                .thenReturn(true);

        when(jwtService.generateToken(user.getEmail()))
                .thenReturn("jwt-token");

        when(userMapper.toResponse(user))
                .thenReturn(authResponse);

        AuthResponse result = service.login(loginRequest);

        assertNotNull(result);
        assertEquals("jwt-token", result.getToken());

        verify(userRepository).findByEmail(loginRequest.getEmail());
        verify(passwordEncoder).matches(loginRequest.getPassword(), user.getPassword());
        verify(jwtService).generateToken(user.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> service.login(loginRequest)
        );

        verify(userRepository).findByEmail(loginRequest.getEmail());
        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsInvalid() {

        when(userRepository.findByEmail(loginRequest.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword()))
                .thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.login(loginRequest)
        );

        assertEquals("Invalid credentials", exception.getMessage());

        verify(jwtService, never()).generateToken(anyString());
    }
}