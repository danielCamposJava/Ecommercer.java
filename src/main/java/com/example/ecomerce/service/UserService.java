package com.example.ecomerce.service;

import com.example.ecomerce.dto.request.UserRequest;
import com.example.ecomerce.dto.response.UserResponse;
import com.example.ecomerce.entity.UserEntity;
import com.example.ecomerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        UserEntity entity = new UserEntity();

        entity.setName(request.name());
        entity.setEmail(request.email());
        entity.setPassword(passwordEncoder.encode(request.password()));
        entity.setRole("USER");

        entity.setPhone(request.phone());
        entity.setAddress(request.address());
        entity.setCity(request.city());
        entity.setState(request.state());
        entity.setCountry(request.country());
        entity.setZip(request.zip());

        UserEntity saved = userRepository.save(entity);

        return UserResponse.fromEntity(saved);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    public UserResponse updateUser(UUID id, UserRequest request) {

        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        entity.setName(request.name());
        entity.setEmail(request.email());
        entity.setPassword(passwordEncoder.encode(request.password()));

        entity.setPhone(request.phone());
        entity.setAddress(request.address());
        entity.setCity(request.city());
        entity.setState(request.state());
        entity.setCountry(request.country());
        entity.setZip(request.zip());

        UserEntity saved = userRepository.save(entity);
        return UserResponse.fromEntity(saved);
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(id);
    }
}