package com.example.ecomerce.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cart_items")
@Getter
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // DONO DA RELAÇÃO
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;

    // RELAÇÃO COM PRODUTO
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal price;

    //  construtor protegido (JPA)
    protected CartItemEntity() {}

    //  construtor correto (regra de negócio)
    public CartItemEntity(ProductEntity product, int quantity, BigDecimal price) {

        if (product == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        if (price == null) {
            throw new IllegalArgumentException("Preço não pode ser nulo");
        }

        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public CartItemEntity(ProductEntity product, int quantity, double price) {
    }

    // controle interno (evita bagunça externa)
    void setCart(CartEntity cart) {
        this.cart = cart;
    }

    public void increaseQuantity(int amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        this.quantity += amount;
    }

    public void updateQuantity(int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}