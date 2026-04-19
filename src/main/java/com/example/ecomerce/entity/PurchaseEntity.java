package com.example.ecomerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore //  Escondido para bater com seu exemplo
    private UUID id;

    // Removido FetchType.LAZY para evitar o 403 se a sessão fechar antes da renderização
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    @JsonIgnore // Escondido para bater com seu exemplo
    private BigDecimal priceAtPurchase;

    @Column(nullable = false)
    @JsonIgnore //  Escondido para bater com seu exemplo
    private LocalDateTime purchaseDate;

    public PurchaseEntity() {
        this.purchaseDate = LocalDateTime.now();
    }

    public void setPrice(BigDecimal price) {
        this.priceAtPurchase = price;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
        if (product != null && this.priceAtPurchase == null) {
            this.priceAtPurchase = BigDecimal.valueOf(product.getPrice());
        }
    }

    @JsonIgnore //  Escondido para bater com seu exemplo
    public BigDecimal getTotal() {
        return priceAtPurchase != null ?
                priceAtPurchase.multiply(BigDecimal.valueOf(quantity)) : BigDecimal.ZERO;
    }
}
