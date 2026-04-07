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

    private  final CartRepository cartRepository;
    private  final CartItemRepository cartItemRepository;
    private  final UserRepository userRepository;
    private  final ProductRepository productRepository;
    private  final PurchaseRepository purchaseRepository;

    //Cria ou retorna o carrinho ativo
    public CartEntity getOrCreateCart(UserEntity user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    CartEntity cart = new CartEntity();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    //Adiciona um produto ao carrinho
    public  void addProductToCart(UUID userId, UUID productId, int quantity){
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        ProductEntity product = productRepository.findById(productId).
                orElseThrow( () -> new RuntimeException("Product not found"));

        CartEntity cart = getOrCreateCart(user);

        CartEntity item = new CartItemEntity().getCart();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);

        cart.getItems().add(item);
        cartRepository.save(cart);
    }

    //removae o prpduto do carrinho
    public void removeProductFromCart(UUID userId, UUID prodcutId){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow( () -> new RuntimeException("User Not Found") );

        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow( () -> new RuntimeException("Cart Not Found "));
        cart.getItems().removeIf(i -> i.getProduct().getId().equals(prodcutId));

        cartRepository.save(cart);
    }

    public List<CartItemEntity>getCart(UUID  userId ){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow( () -> new RuntimeException("User Not Found") );

        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow( () -> new RuntimeException("Cart Not Found "));

        return cart.getItems();

    }

    //finaliza Compra
    public  void Checkout( UUID userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow( () -> new RuntimeException("User Not Found") );

        CartEntity cart = cartRepository.findByUser(user)
                .orElseThrow( () -> new RuntimeException("Cart Not Found "));


        if(cart.getItems().isEmpty()){
            throw  new RuntimeException("Cart is empty");

        }

        //Cria o históridico
        for(CartItemEntity item : cart.getItems()){
            PurchaseEntity purchase = new PurchaseEntity();
            purchase.setUser(user);
            purchase.setProduvt(item.getProduct());
            purchase.setQuantity(item.getQuantity());

            purchaseRepository.save(purchase);
        }

        //Limpa Carrinho
        cart.getItems().clear();
        cartRepository.save(cart);


    }







}
