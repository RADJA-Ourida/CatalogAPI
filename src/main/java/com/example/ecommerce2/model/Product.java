package com.example.ecommerce2.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Double price;
    private String name;


    public Product(Double price, String name) {
        this.price = price;
        this.name = name;

    }

    public Boolean isPriceMoreTen(Product product){
        if (product.getPrice()>=10) return true;
        return false;
    }
}
