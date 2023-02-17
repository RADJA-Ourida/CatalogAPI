package com.example.ecommerce2.repository;

import com.example.ecommerce2.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock,Integer> {


    Stock findStockByProductId(Integer productId);

    Stock findStockById(Integer id);
}
