package com.example.ecommerce2.controller;

import com.example.ecommerce2.model.*;
import com.example.ecommerce2.modelAPI.CatalogAPI;
import com.example.ecommerce2.modelAPI.CategoryAPI;
import com.example.ecommerce2.modelAPI.ProductAPI;
import com.example.ecommerce2.service.CatalogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class CatalogController {

    private final CatalogService catalogService;


    @PostMapping("/newProduct")
    public ResponseEntity<Product> createProduct (@RequestBody ProductAPI productAPI){
        if (productAPI.getPrice()<0){ throw new IllegalArgumentException("Price must be positive !");}
        return new ResponseEntity<>( catalogService.createProduct(productAPI), HttpStatus.CREATED);
    }


    @PostMapping("/addProductStock/{idProduct}")
    public ResponseEntity<Stock> addProductStock(@PathVariable("idProduct") Integer idProduct, @RequestBody Integer qnt){

        if (qnt<0){throw new IllegalArgumentException("Quantity must be positive");}
        Stock stock = catalogService.addProductTOStock(idProduct, qnt);
        return new ResponseEntity<>(stock, HttpStatus.CREATED);
    }


    @PostMapping("/newCart")
    public ResponseEntity<Cart> createCart (@RequestBody List<Integer> idProductList){

        return new ResponseEntity<>(catalogService.createCart(idProductList), HttpStatus.CREATED);

    }

    @PostMapping("/newCategory")
    public ResponseEntity<Category> createCategory (@RequestBody CategoryAPI categoryAPI){
        return new ResponseEntity<>(catalogService.createCategory(categoryAPI), HttpStatus.CREATED);
    }


    @PostMapping("/newCatalog")
    public ResponseEntity<Catalog> createCatalog (@RequestBody CatalogAPI catalogAPI){
        return new ResponseEntity<>(catalogService.createCatalog(catalogAPI), HttpStatus.CREATED);
    }



}
