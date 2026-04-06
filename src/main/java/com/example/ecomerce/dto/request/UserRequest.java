package com.example.ecomerce.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserRequest (

        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password,

        @NotBlank String phone,
        @NotBlank String address,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String country,
        @NotBlank String zip
) {}