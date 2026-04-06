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

    @Setter
    @Column(nullable = false)
    private UUID userId;

    @OneToMany(mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<CartItemEntity> items = new ArrayList<>();

    //  evita alteração externa da lista
    public List<CartItemEntity> getItems() {
        return Collections.unmodifiableList(items);
    }

    //  busca item por produto
    public Optional<CartItemEntity> findItemByProduct(UUID productId) {
        return items.stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();
    }

    // adicionar produto
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
        } else {
            CartItemEntity newItem =
                    new CartItemEntity(product, quantity, product.getPrice());
            addItem(newItem);
        }
    }

    // controla consistência da relação
    private void addItem(CartItemEntity item) {
        items.add(item);
        item.setCart(this);
    }

    //  total do carrinho
    public BigDecimal getTotal() {
        return items.stream()
                .map(i -> i.getPrice()
                        .multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    //  atualizar quantidade
    public void updateProductQuantity(UUID productId, int quantity) {

        if (quantity < 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        CartItemEntity item = findItemByProduct(productId)
                .orElseThrow(() -> new RuntimeException("Produto não está no carrinho"));

        if (quantity == 0) {
            removeProduct(productId);
        } else {
            item.updateQuantity(quantity);
        }
    }

    //  remover produto
    public void removeProduct(UUID productId) {

        CartItemEntity item = findItemByProduct(productId)
                .orElseThrow(() -> new RuntimeException("Produto não está no carrinho"));

        items.remove(item);
        item.setCart(null); // 🔥 importante pro orphanRemoval funcionar
    }

    // evita loop infinito no log
    @Override
    public String toString() {
        return "CartEntity{id=" + id + ", total=" + getTotal() + "}";
    }
}