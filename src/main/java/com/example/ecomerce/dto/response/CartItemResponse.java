package com.example.ecomerce.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemResponse(
        UUID productId,
        int quantity,
        BigDecimal price,
        BigDecimal total
)  {
}
