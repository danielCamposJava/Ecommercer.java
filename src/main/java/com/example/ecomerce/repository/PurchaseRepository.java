package com.example.ecomerce.repository;

import com.example.ecomerce.entity.ProductEntity;
import com.example.ecomerce.entity.PurchaseEntity;
import com.example.ecomerce.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, UUID> {
    List<PurchaseEntity> findByUser(UserEntity user);
}