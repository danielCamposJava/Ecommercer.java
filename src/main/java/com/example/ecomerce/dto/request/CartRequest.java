package com.example.ecomerce.dto.request;

import java.util.UUID;

public record CartRequest(
        UUID productId,
        int quantity
) {}