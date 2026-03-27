package com.example.ecomerce.dto.response;

import com.example.ecomerce.entity.UserEntity;

public record UserResponse(
        String name,
        String password
) {
    public static  UserResponse fromEntity(UserEntity entity) {
        return new UserResponse(
                entity.getEmail(),
                entity.getPassword()
        );
    }
}
