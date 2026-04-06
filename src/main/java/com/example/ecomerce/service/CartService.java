package com.example.ecomerce.service;

import com.example.ecomerce.entity.CartEntity;
import com.example.ecomerce.entity.ProductEntity;
import com.example.ecomerce.repository.CartRepository;
import com.example.ecomerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartService {

    private  final CartRepository cartReposiotry;
    private final ProductRepository productRepository;

    public  CartService (CartRepository cartReposiotry , ProductRepository productRepository){
         this.cartReposiotry = cartReposiotry;
         this.productRepository = productRepository;
    }


    @Transactional
    public  void addProduct(UUID cartId, UUID productId, int quantity){
        CartEntity cart = getCart(cartId);
        ProductEntity product = getProduct(productId);

        validateStock(product, quantity);

        cartReposiotry.save(cart);
    }

    @Transactional
    public void updateProduct( UUID cartId, UUID prodcutId, int quantity) throws IllegalAccessException {
        CartEntity cart = getCart(cartId);

        if(quantity > 0) {
            ProductEntity  product = getProduct(prodcutId);
            validateStock(product, quantity);

        }

        cart.updateProductQuantity(prodcutId, quantity);
        cartReposiotry.save(cart);

    }
    @Transactional
    public  void removeProduct(UUID cartId, UUID productId){

        CartEntity cart = getCart(cartId);
        cart.removeProduct(productId);
        cartReposiotry.save(cart);
    }

    public CartEntity getCartById(UUID cartId){
        return  getCart(cartId);
    }

    private CartEntity getCart(UUID cartId) {

    return  cartReposiotry.findById(cartId).orElseThrow(
            () -> new RuntimeException("Carrinho não econtrado")
    );
    }

    private ProductEntity getProduct(UUID productId) {
        return  productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("Produto não encontrado")
        );
    }

    private  void validateStock(ProductEntity product, int quantity){
        if( product.getStock() < quantity){
            throw  new RuntimeException("Estoque insulficiente");
        }
    }
}

