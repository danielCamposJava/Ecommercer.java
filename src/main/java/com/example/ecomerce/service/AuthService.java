package com.example.ecomerce.service;

import com.example.ecomerce.dto.request.LoginRequest;
import com.example.ecomerce.dto.response.AuthResponse;
import com.example.ecomerce.entity.UserEntity;
import com.example.ecomerce.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

import static javax.crypto.Cipher.SECRET_KEY;

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