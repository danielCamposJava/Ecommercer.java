package com.example.ecomerce.service;

import com.example.ecomerce.entity.CartEntity;
import com.example.ecomerce.entity.ProductEntity;
import com.example.ecomerce.entity.UserEntity;
import com.example.ecomerce.repository.CartRepository;
import com.example.ecomerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void addProduct(UUID productId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        UserEntity user = getAuthenticatedUser();
        CartEntity cart = getOrCreateCart(user);
        ProductEntity product = getProduct(productId);

        validateStock(product, quantity);

        cart.addProduct(product, quantity);

        cartRepository.save(cart);
    }

    @Transactional
    public void updateProduct(UUID productId, UUID id, int quantity) {

        UserEntity user = getAuthenticatedUser();
        CartEntity cart = getOrCreateCart(user);

        if (quantity > 0) {
            ProductEntity product = getProduct(productId);
            validateStock(product, quantity);
        }

        cart.updateProductQuantity(productId, quantity);

        cartRepository.save(cart);
    }

    @Transactional
    public void removeProduct(UUID productId, UUID id) {

        UserEntity user = getAuthenticatedUser();
        CartEntity cart = getOrCreateCart(user);

        cart.removeProduct(productId);

        cartRepository.save(cart);
    }

    public CartEntity getMyCart() {
        UserEntity user = getAuthenticatedUser();
        return getOrCreateCart(user);
    }

    // =========================
    // MÉTODOS PRIVADOS
    // =========================

    private CartEntity getOrCreateCart(UserEntity user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    private ProductEntity getProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    private void validateStock(ProductEntity product, int quantity) {
        if (product.getStock() < quantity) {
            throw new RuntimeException("Estoque insuficiente");
        }
    }

    private UserEntity getAuthenticatedUser() {

        // depende de como você salvou no JWT
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserEntity user) {
            return user;
        }

        throw new RuntimeException("Usuário não autenticado");
    }

    public CartEntity getCartById(UUID cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }
}
