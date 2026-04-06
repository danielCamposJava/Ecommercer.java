package com.example.ecomerce.dto.response;

import com.example.ecomerce.entity.UserEntity;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        String password,
        String role,
        String phone,
        String address,
        String city,
        String state,
        String country,
        String zip
) {

    public static UserResponse fromEntity(UserEntity entity) {
        return new UserResponse(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getCity(),
                entity.getState(),
                entity.getCountry(),
                entity.getZip()
        );
    }
}