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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    @ManyToMany
    @JoinTable(name = "category_product",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private List<Product> productList;
    @OneToMany
    private List<Category> subCategoryList;


    public Category(String name, String description, List<Product> productList,
                    List<Category> subCategoryList) {
        this.name = name;
        this.description = description;
        this.productList = productList;
        this.subCategoryList = subCategoryList;
    }
}
