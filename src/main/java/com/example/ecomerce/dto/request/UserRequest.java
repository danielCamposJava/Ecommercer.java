package com.example.ecomerce.dto.request;

import jakarta.validation.constraints.NotBlank;

import javax.swing.*;

public record UserRequest (

        @NotBlank
        String name,

        @NotBlank
        String email,

        @NotBlank
        String password

){
}
