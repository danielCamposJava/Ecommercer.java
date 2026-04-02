package com.example.ecomerce.repository;

import com.example.ecomerce.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProductRepository extends
        JpaRepository<ProductEntity, UUID>,
        JpaSpecificationExecutor<ProductEntity> {
}