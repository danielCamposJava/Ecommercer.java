package com.example.ecomerce.dto.response;

import com.example.ecomerce.entity.ProductEntity;

import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String description,
        String category,
        double price
) {

    public static ProductResponse of(ProductEntity entity) {
        if (entity == null) return null;

        return new ProductResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCategory(),
                entity.getPrice()
        );
    }
}
