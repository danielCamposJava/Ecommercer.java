package com.example.ecomerce.configSecurity;

import com.example.ecomerce.entity.UserEntity;
import com.example.ecomerce.repository.UserRepository;
import com.example.ecomerce.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // pega header Authorization
        String authHeader = request.getHeader("Authorization");

        //  se não tem token, segue normal
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //  remove "Bearer "
        String token = authHeader.substring(7);

        // extrai email do token
        String email = jwtService.extractEmail(token);

        // valida se ainda não está autenticado
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserEntity user = (UserEntity) userRepository.findByEmail(email)
                    .orElse(null);

            if (user != null) {

                // cria autenticação no Spring
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                Collections.emptyList()
                        );

                //  seta no contexto
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}