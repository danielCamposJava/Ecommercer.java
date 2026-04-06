package com.example.ecomerce.repository;

import com.example.ecomerce.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    //  buscar por email (ESSENCIAL pra login)
    Optional<UserEntity> findByEmail(String email);

    //  verificar se email já existe (evita duplicado)
    boolean existsByEmail(String email);
}