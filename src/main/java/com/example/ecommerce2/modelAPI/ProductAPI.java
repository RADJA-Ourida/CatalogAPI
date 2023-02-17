package com.example.ecommerce2.modelAPI;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ProductAPI {

    private Integer id;

    private Double price;
    private String name;


    public ProductAPI(Double price, String name) {
        this.price = price;
        this.name = name;

    }
}
