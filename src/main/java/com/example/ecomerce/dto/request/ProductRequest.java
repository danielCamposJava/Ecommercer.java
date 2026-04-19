package com.example.ecomerce.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// Removido o <stock> genérico que estava causando erro de sintaxe
public record ProductRequest(

        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotBlank(message = "A descrição é obrigatória")
        String description,

        @NotBlank(message = "A categoria é obrigatória")
        String category,

        @NotNull(message = "O preço é obrigatório")
        @Min(0)
        Double price, // Alterado para Double para cálculos

        @NotNull(message = "A quantidade inicial é obrigatória")
        @Min(0)
        Integer quantity, // Alterado para Integer

        @NotNull(message = "O estoque é obrigatório")
        @Min(0)
        Integer stock // Alterado para Integer para funcionar na regra de negócio
) {
}