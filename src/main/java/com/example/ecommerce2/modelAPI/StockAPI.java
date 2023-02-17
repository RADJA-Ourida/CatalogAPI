package com.example.ecommerce2.modelAPI;

import com.example.ecommerce2.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;

public class StockAPI {


    private Integer id;
    private Integer quantityStock;

    private ProductAPI product;

    public StockAPI(Integer quantityStock, ProductAPI product) {
        this.quantityStock = quantityStock;
        this.product = product;
    }
}
