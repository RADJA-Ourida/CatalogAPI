package com.example.ecommerce2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table
public class ProductBought {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Product product;
    private Integer quantityBought;

    public ProductBought(Product product, Integer quantityBought) {
        this.product = product;
        this.quantityBought = quantityBought;
    }
}
