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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false) // H2 não aceitará NULL aqui
    private BigDecimal price;

    protected CartItemEntity() {}

    // CORREÇÃO: Este construtor agora atribui o preço corretamente
    public CartItemEntity(ProductEntity product, int quantity, double price) {
        if (product == null) throw new IllegalArgumentException("Produto nulo");
        if (quantity <= 0) throw new IllegalArgumentException("Quantidade inválida");

        this.product = product;
        this.quantity = quantity;
        this.price = BigDecimal.valueOf(price); // Atribui o valor convertido
    }

    void setCart(CartEntity cart) {
        this.cart = cart;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}