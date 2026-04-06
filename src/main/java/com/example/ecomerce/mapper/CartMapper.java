package com.example.ecomerce.mapper;

import com.example.ecomerce.dto.response.CartItemResponse;
import com.example.ecomerce.dto.response.CartResponse;
import com.example.ecomerce.entity.CartEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {

    public CartResponse toResponse(CartEntity cart ){

        List<CartItemResponse> items = cart.getItems()
                .stream().map(item -> new CartItemResponse(
                        item.getProduct().getId(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getTotal()
                )).toList();

        return  new CartResponse(
                cart.getId(),
                items,
                cart.getTotal()
        );
    }
}
