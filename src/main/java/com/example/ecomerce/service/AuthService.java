package com.example.ecomerce.service;

import com.example.ecomerce.dto.request.LoginRequest;
import com.example.ecomerce.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {
        // Valida email e senha (se estiver errado, lança exceção automaticamente)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // Se chegou aqui, está validado. Gera o token.
        String token = jwtService.generateToken(request.email());
        return new AuthResponse(token);
    }
}