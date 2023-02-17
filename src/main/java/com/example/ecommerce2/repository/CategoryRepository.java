package com.example.ecommerce2.repository;

import com.example.ecommerce2.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    public Category findCategoryById(Integer idCategory);
}
