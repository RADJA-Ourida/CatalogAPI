package com.example.ecommerce2.modelAPI;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class CartAPI {

    private Integer id;
    private List<Integer> idProductList;


    public CartAPI(List<Integer> productList) {
        this.idProductList = idProductList;
    }
}
