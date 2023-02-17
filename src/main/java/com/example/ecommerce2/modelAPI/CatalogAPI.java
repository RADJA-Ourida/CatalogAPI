package com.example.ecommerce2.modelAPI;

import com.example.ecommerce2.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class CatalogAPI {

    private Integer id;
    private List<Integer> idCategoryList;
    public CatalogAPI(List<Integer> idCategoryList) {
        this.idCategoryList = idCategoryList;
    }
}
