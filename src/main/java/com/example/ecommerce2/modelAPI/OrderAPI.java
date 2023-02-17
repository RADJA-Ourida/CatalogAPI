package com.example.ecommerce2.modelAPI;

import com.example.ecommerce2.model.ProductBought;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@NoArgsConstructor
@Setter
@Getter
public class OrderAPI {

    private Integer id;
    private Date orderDate;
    private Double totalOrder;

    private List<ProductBoughtAPI> productBoughtList ;

    public OrderAPI(Date orderDate, Double totalOrder, List<ProductBoughtAPI> productBoughtList) {
        this.orderDate = orderDate;
        this.totalOrder = totalOrder;
        this.productBoughtList = productBoughtList;
    }
}
