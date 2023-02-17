package com.example.ecommerce2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @ManyToMany
    @JoinTable(name = "cart_product",
            joinColumns = @JoinColumn (name="cart_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name= "product_id", referencedColumnName = "id"))
    private List<Product> productList;

    public Cart(List<Product> productList) {
        this.productList = productList;
    }
}
