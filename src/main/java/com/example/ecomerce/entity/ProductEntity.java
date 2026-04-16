package com.example.ecomerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "product")
@Getter
@Setter
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Nome pode ser único dependendo do negócio (mantive SEM unique por segurança)
    @Column(nullable = false)
    private String name;

    // Nunca deve ser unique
    @Column(nullable = false, length = 500)
    private String description;

    // Nunca deve ser unique
    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private int quantity;

    @Version // Controle de concorrência (otimista)
    private Long version;

    // =========================
    // REGRAS DE NEGÓCIO
    // =========================

    public void decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        if (this.stock < quantity) {
            throw new RuntimeException("Estoque insuficiente");
        }

        this.stock -= quantity;
    }

    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        this.stock += quantity;
    }

    // =========================
    // CONSTRUTORES
    // =========================

    public ProductEntity() {}

    public ProductEntity(String name, String description, String category, double price, int stock) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.quantity = 0;
    }
}