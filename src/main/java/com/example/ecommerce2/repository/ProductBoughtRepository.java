package com.example.ecommerce2.repository;

import com.example.ecommerce2.model.ProductBought;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBoughtRepository extends JpaRepository<ProductBought,Integer> {
}
