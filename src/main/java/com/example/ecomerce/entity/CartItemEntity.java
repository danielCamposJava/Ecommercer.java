package com.example.ecomerce.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
public class CartItemEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;

    @Getter
    private int quantity;

    @Getter
    private BigDecimal price;

    protected CartItemEntity() {}

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

    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }
        this.quantity += amount;
    }

    public void updateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }
        this.quantity = quantity;
    }

    void setCart(CartEntity cart) {
    }

    public BigDecimal getTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItemEntity that)) return false;
        return product != null && that.product != null &&
                Objects.equals(product.getId(), that.product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(product != null ? product.getId() : null);
    }
}