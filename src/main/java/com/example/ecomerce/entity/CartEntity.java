package com.example.ecomerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "carts")
@Getter
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // 🔥 RELACIONAMENTO CORRETO
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @Setter
    private UserEntity user;

    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CartItemEntity> items = new ArrayList<>();

    // evita alteração externa
    public List<CartItemEntity> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Optional<CartItemEntity> findItemByProduct(UUID productId) {
        return items.stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();
    }

    public void addProduct(ProductEntity product, int quantity) {

        if (product == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        Optional<CartItemEntity> existingItem = findItemByProduct(product.getId());

        if (existingItem.isPresent()) {
            existingItem.get().increaseQuantity(quantity);
            return;
        }

        CartItemEntity newItem =
                new CartItemEntity(product, quantity, product.getPrice());

        addItem(newItem);
    }

    private void addItem(CartItemEntity item) {
        item.setCart(this);
        items.add(item);
    }

    public BigDecimal getTotal() {
        return items.stream()
                .map(CartItemEntity::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void updateProductQuantity(UUID productId, int quantity) {

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        CartItemEntity item = findItemByProduct(productId)
                .orElseThrow(() -> new RuntimeException("Produto não está no carrinho"));

        if (quantity == 0) {
            removeProduct(productId);
            return;
        }

        item.updateQuantity(quantity);
    }

    public void removeProduct(UUID productId) {

        CartItemEntity item = findItemByProduct(productId)
                .orElseThrow(() -> new RuntimeException("Produto não está no carrinho"));

        items.remove(item);
        item.setCart(null);
    }

    @Override
    public String toString() {
        return "CartEntity{id=" + id + "}";
    }
}