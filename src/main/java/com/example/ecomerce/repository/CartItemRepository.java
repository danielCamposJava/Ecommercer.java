package com.example.ecomerce.repository;

import com.example.ecomerce.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItemEntity, UUID> {
    
}