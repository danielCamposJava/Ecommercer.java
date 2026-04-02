package com.example.ecomerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "User")
public class UserEntity {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Setter
    @Getter
    @Column(nullable = false, unique = true)
    private String name;

    @Setter
    @Getter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Getter
    @Column(nullable = false)
    private String password;

    @Setter
    @Getter
    @Column(nullable = false)
    private String phone;

    @Setter
    @Getter
    @Column(nullable = false)
    private String address;

    @Setter
    @Getter
    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String zip;


    public UserEntity(String name, String email, String password, String phone, String address, String city, String state, String country, String zip) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
    }

    public UserEntity() {

    }

}
