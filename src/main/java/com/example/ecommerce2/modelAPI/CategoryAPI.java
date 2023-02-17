package com.example.ecommerce2.modelAPI;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class CategoryAPI {
    private Integer id;
    private String name;
    private String description;
    private List<Integer> idProductList;
    private List<Integer> idSubCategoryList;


    public CategoryAPI(String name, String description, List<Integer> idProductList,
                       List<Integer> idSubCategoryList) {
        this.name = name;
        this.description = description;
        this.idProductList = idProductList;
        this.idSubCategoryList = idSubCategoryList;
    }
}
