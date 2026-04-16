package com.example.ecomerce.service;

import com.example.ecomerce.entity.CartEntity;
import com.example.ecomerce.entity.ProductEntity;
import com.example.ecomerce.entity.UserEntity;
import com.example.ecomerce.repository.CartRepository;
import com.example.ecomerce.repository.ProductRepository;
import com.example.ecomerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final UserRepository  userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    // =========================
    // PUBLIC METHODS
    // =========================

    public void addProduct(UUID productId, int quantity) {

        validateQuantity(quantity);

        UserEntity user = getAuthenticatedUser();
        CartEntity cart = getOrCreateCart(user);
        ProductEntity product = getProduct(productId);

        validateStock(product, quantity);

        cart.addProduct(product, quantity);

        cartRepository.save(cart);
    }

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

    public void removeProduct(UUID productId, UUID id) {

        UserEntity user = getAuthenticatedUser();
        CartEntity cart = getOrCreateCart(user);

        cart.removeProduct(productId);

        cartRepository.save(cart);
    }

    public CartEntity getMyCart() {
        return getOrCreateCart(getAuthenticatedUser());
    }

    // =========================
    // PRIVATE METHODS
    // =========================

    private CartEntity getOrCreateCart(UserEntity user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    CartEntity cart = new CartEntity();
                    cart.setUser(user);
                    return cartRepository.save(cart);
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

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }
    }

    private UserEntity getAuthenticatedUser() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserEntity user) {
            return user;
        }

        throw new RuntimeException("Usuário inválido");
    }
}