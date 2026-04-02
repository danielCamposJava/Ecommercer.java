package com.example.ecomerce.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.util.UUID;

@Entity
@Table(name = "product")
@SQLDelete( sql = "UPDATE product SET deleted = true WHERE id =?")
public class ProductEntity {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @Getter
    @Column( nullable = false, unique = true)
    private String name;

    @Setter
    @Getter
    @Column( nullable = false, unique = true)
    private String description;

    @Setter
    @Getter
    @Column(nullable = false, unique = true)
    private String category;

    @Setter
    @Getter
    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int quantity;


    public ProductEntity(String name, String description, String category, double price, int quantity, double unitPrice) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
}

    public ProductEntity() {

    }


}
