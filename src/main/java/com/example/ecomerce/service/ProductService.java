package com.example.ecomerce.service;

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

    public ProductResponse addProduct(@Valid  ProductResponse request ) {

            if(productRepository.existsById(request.id())){
                throw new IllegalArgumentException("product already exists");
            }

            ProductEntity productEntity = new ProductEntity();

            return new ProductResponse(
                    UUID.randomUUID(), request.name(),
                    request.description(),
                    request.category(),
                    request.price()
                    );

    }

   public List<ProductResponse> getAllProducts( ){
        return (List<ProductResponse>) productRepository.
                findAll().stream().map(products ->  new ProductResponse(
                        products.getId(),
                        products.getName(),
                        products.getDescription(),
                        products.getCategory(),
                        products.getPrice()
                ));
   }

   public ProductResponse updateProduct(@Valid  ProductResponse request ) {

      ProductEntity productEntity = productRepository.findById(id).orElseThrow(
              () -> new IllegalArgumentException("product not found")
      );

      if(!productEntity.getName().equals((productEntity.getName()))){
          throw new IllegalArgumentException("product name does not match");
      }
      productEntity.setDescription(request.description());
      productEntity.setCategory(request.category());

      return new ProductResponse(
              UUID.randomUUID(), request.name(),
              request.description(),
              request.category(),
              request.price()

      );

   }

   public ProductResponse deleteProduct(UUID id ) {
        if(productRepository.existsById(id)){
            throw new RuntimeException("product not found");
        }

        productRepository.deleteById(id);
        return new ProductResponse(
                UUID.randomUUID(), request.name(),
                request.description(),
                request.category(),
                request.price()
        );
   }
}
