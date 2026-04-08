package com.example.ecomerce.service;

import com.example.ecomerce.entity.*;
import com.example.ecomerce.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    //  Busca ou cria carrinho
    public CartEntity getOrCreateCart(UserEntity user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    CartEntity cart = new CartEntity();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    //  Adicionar produto
    public void addProductToCart(UUID userId, UUID productId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        UserEntity user = getUser(userId);
        ProductEntity product = getProduct(productId);

        validateStock(product, quantity);

        CartEntity cart = getOrCreateCart(user);

        //  usa o domínio corretamente
        cart.addProduct(product, quantity);

        cartRepository.save(cart);
    }

    //  Remover produto
    public void removeProductFromCart(UUID userId, UUID productId) {

        UserEntity user = getUser(userId);
        CartEntity cart = getCart(user);

        cart.removeProduct(productId);

        cartRepository.save(cart);
    }

    //  Visualizar carrinho
    public List<CartItemEntity> getCart(UUID userId) {

        UserEntity user = getUser(userId);
        CartEntity cart = getCart(user);

        return cart.getItems();
    }

    //  Checkout
    public void checkout(UUID userId) {

        UserEntity user = getUser(userId);
        CartEntity cart = getCart(user);

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Carrinho vazio");
        }

        //  valida estoque novamente (CRÍTICO)
        for (CartItemEntity item : cart.getItems()) {
            validateStock(item.getProduct(), item.getQuantity());
        }

        //  cria histórico
        for (CartItemEntity item : cart.getItems()) {

            PurchaseEntity purchase = new PurchaseEntity();
            purchase.setUser(user);
            purchase.setProduct(item.getProduct()); // corrigido
            purchase.setQuantity(item.getQuantity());

            purchaseRepository.save(purchase);

            //  baixa estoque (simples)
            ProductEntity product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
        }

        //  limpa carrinho
        cart.getItems().clear();

        cartRepository.save(cart);
    }

    // Métodos auxiliares

    private UserEntity getUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    private ProductEntity getProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    private CartEntity getCart(UserEntity user) {
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
    }

    private void validateStock(ProductEntity product, int quantity) {
        if (product.getStock() < quantity) {
            throw new RuntimeException("Estoque insuficiente");
        }
    }

    public List<PurchaseEntity> getPurchases(UUID userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return purchaseRepository.findByUser(user);
    }
    }





