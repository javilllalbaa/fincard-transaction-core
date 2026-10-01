package com.fincard.authorization.application;

import com.fincard.authorization.domain.model.User;
import com.fincard.authorization.domain.repository.UserRepository;
import com.fincard.authorization.infrastructure.security.JwtService;
import com.fincard.authorization.interfaces.dto.LoginRequest;
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
class AuthServiceTest {
    @Mock UserRepository repository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock JwtService jwtService;
    @InjectMocks AuthService service;

    @Test
    void shouldLoginSuccessfully() {
        var user = new User("demo", "hash", "USER");
        when(repository.findByUsername("demo")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("secret", "hash")).thenReturn(true);
        when(jwtService.generateToken("demo", "USER")).thenReturn("jwt");
        when(jwtService.getExpirationMs()).thenReturn(3600000L);

        var result = service.login(new LoginRequest("demo", "secret"));

        assertEquals("jwt", result.accessToken());
        assertEquals("Bearer", result.tokenType());
        verify(jwtService).generateToken("demo", "USER");
    }

    @Test
    void shouldRejectInvalidPassword() {
        var user = new User("demo", "hash", "USER");
        when(repository.findByUsername("demo")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("bad", "hash")).thenReturn(false);

        assertThrows(Exception.class, () ->
                service.login(new LoginRequest("demo", "bad")));
        verify(jwtService, never()).generateToken(anyString(), anyString());
    }
}
