package com.fincard.authorization.application;

import com.fincard.authorization.domain.repository.UserRepository;
import com.fincard.authorization.infrastructure.security.JwtService;
import com.fincard.authorization.interfaces.dto.LoginRequest;
import com.fincard.authorization.interfaces.dto.LoginResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        var user = repository.findByUsername(request.username())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return new LoginResponse(
                jwtService.generateToken(user.getUsername(), user.getRole()),
                "Bearer",
                jwtService.getExpirationMs()
        );
    }
}
