package com.example.ecomerce.service;

import com.example.ecomerce.dto.request.ProductRequest;
import com.example.ecomerce.dto.response.ProductResponse;
import com.example.ecomerce.entity.ProductEntity;
import com.example.ecomerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse addProduct(@Valid ProductRequest request) {

        ProductEntity entity = new ProductEntity();
        entity.setId(UUID.randomUUID());
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setCategory(request.category());
        entity.setPrice(Double.parseDouble(request.price()));

        ProductEntity saved = productRepository.save(entity);

        return ProductResponse.of(saved);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::of)
                .toList();
    }


    public ProductResponse updateProduct(UUID id, @Valid ProductRequest request) {

        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("product not found"));

        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setCategory(request.category());
        entity.setPrice(Double.parseDouble(request.price()));

        return ProductResponse.of(entity);
    }

    // DELETE
    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("product not found");
        }

        productRepository.deleteById(id);
    }
}
