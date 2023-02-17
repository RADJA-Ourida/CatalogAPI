package com.example.ecommerce2.modelAPI;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class ProductBoughtAPI {
    private Integer id;
    private ProductAPI product;
    private Integer quantityBought;
    public ProductBoughtAPI(ProductAPI product, Integer quantityBought) {
        this.product = product;
        this.quantityBought = quantityBought;
    }
}
