package com.example.ecomerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

@Entity
public class CartEntity {

    @Getter
    @Id
    @GeneratedValue
    private UUID id;

    @Setter
    @Getter
    private UUID userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> items = new ArrayList<>();

    public List<CartItemEntity> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Optional<CartItemEntity> findItemByProduct(UUID productId) {
        return items.stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();
    }

    public void addProduct( ProductEntity product, int quantity) {

        if (product == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        Optional<CartItemEntity> existingItem = findItemByProduct(product.getId());

        if (existingItem.isPresent()) {
            CartItemEntity item = existingItem.get();
            item.increaseQuantity(quantity);
        } else {
            CartItemEntity newItem = new CartItemEntity(product, quantity, product.getPrice());
            addItem(newItem);
        }
    }

    private void addItem(CartItemEntity item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo");
        }
        items.add(item);
        item.setCart(this);
    }

    public void removeProduct(UUID productId) {
        CartItemEntity item = findItemByProduct(productId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        items.remove(item);
        item.setCart(null);
    }

    public BigDecimal getTotal() {
        return items.stream()
                .map(i -> i.getPrice()
                        .multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}