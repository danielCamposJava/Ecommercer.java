package com.example.ecomerce.service;

import com.example.ecomerce.dto.request.ProductFilter;
import com.example.ecomerce.dto.request.ProductRequest;
import com.example.ecomerce.dto.response.ProductResponse;
import com.example.ecomerce.entity.ProductEntity;
import com.example.ecomerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse addProduct(@Valid ProductRequest request) {
        ProductEntity entity = new ProductEntity();

        // Copiando os dados do DTO para a Entidade
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setCategory(request.category());

        // gora não precisa de Double.parseDouble() porque o DTO já traz números
        entity.setPrice(request.price());
        entity.setStock(request.stock());      // Salvando o estoque!
        entity.setQuantity(request.quantity()); // Salvando a quantidade inicial!

        ProductEntity saved = productRepository.save(entity);
        return ProductResponse.of(saved);
    }

    public List<ProductResponse> getAllProduct() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::of)
                .toList();
    }

    public ProductResponse updateProduct(UUID id, @Valid ProductRequest request) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setCategory(request.category());
        entity.setPrice(request.price());
        entity.setStock(request.stock());      // Atualizando o estoque também
        entity.setQuantity(request.quantity());

        ProductEntity saved = productRepository.save(entity);
        return ProductResponse.of(saved);
    }

    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado");
        }
        productRepository.deleteById(id);
    }
}