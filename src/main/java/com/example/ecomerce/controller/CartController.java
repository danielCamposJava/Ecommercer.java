package com.example.ecomerce.controller;

import com.example.ecomerce.dto.response.CartResponse;
import com.example.ecomerce.entity.CartEntity;
import com.example.ecomerce.mapper.CartMapper;
import com.example.ecomerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    private  final CartService cartService;
    private  final CartMapper cartMapper;

    public  CartController( CartService cartService , CartMapper cartMapper){
        this.cartService = cartService;
        this.cartMapper = cartMapper;

    }

    @PostMapping("/cart/add")
    public ResponseEntity<Void> addProduct(
            @RequestParam UUID productId,
            @RequestParam int quantity) {

        cartService.addProduct(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{cartId}/items")
    public  ResponseEntity<Void>  updateProduct(
            @PathVariable UUID cartId,
            @RequestParam UUID productId,
            @RequestParam int quantity
    ) throws IllegalAccessException {
        cartService.updateProduct(cartId,productId,quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Void> removeProduct(
            @PathVariable UUID cartId,
            @PathVariable UUID productId
    ){
        cartService.removeProduct(cartId,productId);
        return  ResponseEntity.ok().build();
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCart(
            @PathVariable UUID cartId
    ){
        CartEntity cart = cartService.getCartById(cartId);
        return ResponseEntity.ok(cartMapper.toResponse(cart));
    }
}
