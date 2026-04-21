package com.example.ecomerce.service;

import com.example.ecomerce.entity.UserEntity;
import com.example.ecomerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //  Busca o usuário no banco usando o método que você criou
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        //  Converte sua UserEntity para o UserDetails do Spring Security
        // Nota: Se sua UserEntity já implementar UserDetails, você pode retornar 'user' direto.
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword()) // Deve estar em BCrypt
                .authorities(user.getRole().name()) // Assume que você tem um Enum Role
                .build();
    }
}