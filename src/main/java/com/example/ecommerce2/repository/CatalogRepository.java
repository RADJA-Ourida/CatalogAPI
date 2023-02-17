package com.example.ecommerce2.repository;

import com.example.ecommerce2.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog,Integer> {

    public Catalog findCatalogById(Integer idCatalog);
}
