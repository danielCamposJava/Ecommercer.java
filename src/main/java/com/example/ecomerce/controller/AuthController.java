package com.example.ecomerce.controller;

import com.example.ecomerce.dto.request.LoginRequest;
import com.example.ecomerce.dto.response.AuthResponse;
import com.example.ecomerce.service.AuthService;
import lombok.RequiredArgsConstructor;;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @CrossOrigin(origins = "http://localhost:8080") // Corrigido de 'locahost' para 'localhost'
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Agora passamos apenas UM argumento (o objeto request)
        AuthResponse response = authService.login(request);

        // Retornamos o objeto AuthResponse que contém o token
        return ResponseEntity.ok(response);
    }
}