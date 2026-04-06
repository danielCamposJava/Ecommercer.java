package com.example.ecomerce.repository;

import com.example.ecomerce.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<CartEntity, UUID> {


    Optional<CartEntity> findByUserId(UUID userId);
}