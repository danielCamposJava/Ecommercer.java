package com.example.ecomerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
public class PurchaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private ProductEntity product;

    @Setter
    @Getter
    private int quantity;



    public void setUser(UserEntity user) {

    }

    public void setProduct(ProductEntity product) {

    }
}