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
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer quantityStock;
    @OneToOne
    @JoinTable(name = "stock_product",
            joinColumns = @JoinColumn (name="stock_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name= "product_id", referencedColumnName = "id"))
    private Product product;

    public Stock(Integer quantityStock, Product product) {
        this.quantityStock = quantityStock;
        this.product = product;
    }
}
