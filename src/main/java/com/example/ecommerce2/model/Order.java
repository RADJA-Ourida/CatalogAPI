package com.example.ecommerce2.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "customerorder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private Date orderDate;
    private Double totalOrder;
    @OneToMany
    private List<ProductBought> productBoughtList ;

    public Order(Date orderDate, Double totalOrder, List<ProductBought> productBoughtList) {
        this.orderDate = orderDate;
        this.totalOrder = totalOrder;
        this.productBoughtList = productBoughtList;
    }



    public Order(List<ProductBought> productBoughtList) {
        this.productBoughtList = productBoughtList;
    }
}
