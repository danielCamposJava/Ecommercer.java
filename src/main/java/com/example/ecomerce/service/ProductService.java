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
        entity.setId(UUID.randomUUID());
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setCategory(request.category());
        entity.setPrice(Double.parseDouble(request.price()));

        ProductEntity saved = productRepository.save(entity);

        return ProductResponse.of(saved);
    }


    public Page<ProductResponse> getProducts(ProductFilter filter, Pageable pageable){

        Specification<ProductEntity> spec = Specification.where((Specification<ProductEntity>) null);

        if(filter.name() != null && !filter.name().isBlank()){
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("name")),
                            "%" + filter.name().toLowerCase() + "%"));
        }

        if (filter.category() != null && !filter.category().isBlank()) {

            List<String> categories = Arrays.stream(filter.category().split(","))
                    .map(String::trim)
                    .toList();

            spec = spec.and((root, query, cb) ->
                    root.get("category").in(categories));
        }

        if (filter.minPrice() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("price"), filter.minPrice()));
        }

        if (filter.maxPrice() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("price"), filter.maxPrice()));
        }

        return productRepository.findAll(spec, pageable)
                .map(ProductResponse::of);
    }

    public ProductResponse updateProduct(UUID id, @Valid ProductRequest request) {

        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("product not found"));

        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setCategory(request.category());
        entity.setPrice(Double.parseDouble(request.price()));

        ProductEntity saved = productRepository.save(entity);

        return ProductResponse.of(saved);
    }

    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("product not found");
        }

        productRepository.deleteById(id);
    }
}