package com.example.ecomerce.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ProductRequest(

        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotBlank
        String category,

        @NotBlank
        String price,

        @NotBlank
        String quantity

) {


}
