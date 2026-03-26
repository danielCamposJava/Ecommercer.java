package com.example.ecomerce.controller;

import com.example.ecomerce.dto.request.ProductRequest;
import com.example.ecomerce.dto.response.ProductResponse;
import com.example.ecomerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> Create(@Valid @RequestBody ProductResponse request) {
        return  ResponseEntity.ok(productService.addProduct(request));
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ProductResponse>> GetAll() {
        return  ResponseEntity.ok(productService.getAllProducts());
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> Update(@Valid @RequestBody ProductResponse request) {
        return ResponseEntity.ok(productService.updateProduct(request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> Delete(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
