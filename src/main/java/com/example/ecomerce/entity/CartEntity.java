package com.example.ecomerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "carts")
@Getter
@Setter
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> items = new ArrayList<>();

    public void addProduct(ProductEntity product, int quantity) {
        int stockAvailable = product.getStock();
        Optional<CartItemEntity> existingItem = findItemByProduct(product.getId());

        if (existingItem.isPresent()) {
            int newTotal = existingItem.get().getQuantity() + quantity;
            if (newTotal > stockAvailable) {
                throw new RuntimeException("Estoque insuficiente. Disponível: " + stockAvailable);
            }
            existingItem.get().increaseQuantity(quantity);
        } else {
            if (quantity > stockAvailable) {
                throw new RuntimeException("Estoque insuficiente. Disponível: " + stockAvailable);
            }
            CartItemEntity newItem = new CartItemEntity(product, quantity, product.getPrice());
            newItem.setCart(this);
            this.items.add(newItem);
        }
    }

    //  Adicionado: Permite alterar a quantidade diretamente
    public void updateProductQuantity(UUID productId, int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Quantidade inválida");

        if (quantity == 0) {
            removeProduct(productId);
            return;
        }

        CartItemEntity item = findItemByProduct(productId)
                .orElseThrow(() -> new RuntimeException("Produto não está no carrinho"));

        if (quantity > item.getProduct().getStock()) {
            throw new RuntimeException("Estoque insuficiente.");
        }

        item.updateQuantity(quantity);
    }

    //  Corrigido: Remove o item e desvincula do Hibernate
    public void removeProduct(UUID productId) {
        CartItemEntity item = findItemByProduct(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado no carrinho"));

        items.remove(item);
        item.setCart(null);
    }

    // 🔥 Adicionado: Útil para limpar o carrinho após finalizar a compra
    public void clear() {
        items.clear();
    }

    private Optional<CartItemEntity> findItemByProduct(UUID productId) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
    }

    public BigDecimal getTotal() {
        return items.stream()
                .map(CartItemEntity::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}