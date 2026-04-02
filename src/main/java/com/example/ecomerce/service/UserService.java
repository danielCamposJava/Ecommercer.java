package com.example.ecomerce.service;

import com.example.ecomerce.dto.request.UserRequest;
import com.example.ecomerce.dto.response.UserResponse;
import com.example.ecomerce.entity.UserEntity;
import com.example.ecomerce.repository.ProductRepository;
import com.example.ecomerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    public UserResponse createUser(@Valid UserRequest userRequest) {

        UserEntity entity = new UserEntity();
        entity.setId(UUID.randomUUID());
        entity.setEmail(userRequest.email());
        entity.setPassword(userRequest.password());

        UserEntity saved= userRepository.save(entity);

        return UserResponse.of(saved);
    }

    public  List<UserResponse> getAllUser() {
        return  userRepository.
                findAll().
                stream().
                map(UserResponse::of)
                .toList();
    }

    public UserResponse updateUser(UUID id ,@Valid UserRequest userRequest) {

        UserEntity entity = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User nor found")
        );
        entity.setEmail(userRequest.email());
        entity.setPassword(userRequest.password());

        return UserResponse.of(entity);
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }

        userRepository.deleteById(id);
    }
}
