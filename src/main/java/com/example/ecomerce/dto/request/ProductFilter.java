package com.example.ecomerce.dto.request;

import java.math.BigDecimal;

public record ProductFilter(

        String name,
        String category,
        BigDecimal minPrice,
        BigDecimal maxPrice
) {

}