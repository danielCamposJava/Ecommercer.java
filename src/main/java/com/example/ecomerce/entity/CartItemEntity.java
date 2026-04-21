package com.example.ecomerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cart_items")
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Limpa lixo do Hibernate
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    @JsonBackReference // ESSENCIAL: Impede que o item chame o carrinho de volta (evita o loop)
    private CartEntity cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    // Aqui não usamos @JsonIgnore pois você quer ver os detalhes do produto no JSON
    private ProductEntity product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal price;

    protected CartItemEntity() {}

    public CartItemEntity(ProductEntity product, int quantity, double price) {
        if (product == null) throw new IllegalArgumentException("Produto nulo");
        if (quantity <= 0) throw new IllegalArgumentException("Quantidade inválida");

        this.product = product;
        this.quantity = quantity;
        this.price = BigDecimal.valueOf(price);
    }

    void setCart(CartEntity cart) {
        this.cart = cart;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Quantidade inválida");
        this.quantity += amount;
    }

    public void updateQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantidade inválida");
        this.quantity = quantity;
    }

    // 🔥 O Jackson transformará isso em "total": 0.00 no JSON do item
    public BigDecimal getTotal() {
        return price != null ? price.multiply(BigDecimal.valueOf(quantity)) : BigDecimal.ZERO;
    }
}