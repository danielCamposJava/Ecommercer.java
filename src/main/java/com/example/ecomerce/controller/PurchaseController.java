package com.example.ecomerce.controller;

import com.example.ecomerce.entity.PurchaseEntity;
import com.example.ecomerce.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    //  Checkout (finaliza compra)
    @CrossOrigin ( origins= "http://locahost:8080")
    @PostMapping("/{userId}/checkout")
    public ResponseEntity<String> checkout(@PathVariable UUID userId) {

        purchaseService.checkout(userId);
        return ResponseEntity.ok("Compra realizada com sucesso!");
    }

    // Histórico de compras
    @GetMapping("/{userId}")
    public ResponseEntity<List<PurchaseEntity>> getPurchases(@PathVariable UUID userId) {

        return ResponseEntity.ok(purchaseService.getPurchases(userId));
    }
}