package com.example.ecomerce.dto.response;

import com.example.ecomerce.entity.ProductEntity;

import java.util.UUID;

public record ProductResponse (UUID id,
                               String name,
                               String description,
                               String category,
                               double price
){
    public  static ProductResponse FromEntity(ProductEntity entity){
        return  new ProductResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCategory(),
                entity.getPrice()
        );

    }
}
