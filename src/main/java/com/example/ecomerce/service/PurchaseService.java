package com.example.ecomerce.service;

import com.example.ecomerce.entity.*;
import com.example.ecomerce.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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

    // ADICIONADO: Método auxiliar para pegar o usuário logado via JWT
    private UserEntity getAuthenticatedUser() {
        var authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no contexto de segurança"));
    }

    //  Checkout atualizado para usar o usuário logado
    public void checkout(UUID userId) {
        UserEntity user = getAuthenticatedUser();
        CartEntity cart = getCart(user);

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Carrinho vazio");
        }

        // Valida estoque e processa itens
        for (CartItemEntity item : cart.getItems()) {
            ProductEntity product = item.getProduct();

            // Valida estoque (CRÍTICO)
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + product.getName());
            }

            // Cria o registro da compra
            PurchaseEntity purchase = new PurchaseEntity();
            purchase.setUser(user);
            purchase.setProduct(product);
            purchase.setQuantity(item.getQuantity());
            purchase.setPrice(item.getPrice()); //  Salva o preço da época da compra
            purchase.setPurchaseDate(java.time.LocalDateTime.now()); //  Adiciona data

            purchaseRepository.save(purchase);

            // Baixa estoque definitivo
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        // Limpa carrinho usando o método clear que criamos na entidade
        cart.clear();
        cartRepository.save(cart);
    }

    // ADICIONADO: Buscar compras do usuário logado (Histórico)
    public List<PurchaseEntity> getMyPurchases(UUID userId) {
        // 1. Extrai o usuário logado do Token
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // 2. Busca no banco as compras DESTE usuário
        return purchaseRepository.findByUser(user);
    }
    // Métodos auxiliares permanecem
    private CartEntity getCart(UserEntity user) {
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
    }


    // getOrCreateCart e outros métodos podem ser mantidos conforme sua necessidade
}


