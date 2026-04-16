package com.example.ecomerce.controller;

import com.example.ecomerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.jaxb.Origin;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.util.Elements;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;
    @CrossOrigin ( origins= "http://locahost:8080")
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        return authService.login(email, password);
    }
}