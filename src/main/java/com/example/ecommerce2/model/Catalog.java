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
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToMany
    @JoinTable(name = "catalog_category",
            joinColumns = @JoinColumn (name="catalog_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name= "category_id", referencedColumnName = "id"))
    private List<Category> categoryList;

    public Catalog(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
