package com.example.ecommerce2.controller;

import com.example.ecommerce2.model.*;
import com.example.ecommerce2.modelAPI.CatalogAPI;
import com.example.ecommerce2.modelAPI.CategoryAPI;
import com.example.ecommerce2.modelAPI.ProductAPI;
import com.example.ecommerce2.service.CatalogService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api")

public class CatalogController {

   // @Autowired // no need sinince we have a single constructor, Spring will automatically inject it
    private final CatalogService catalogService;


    @GetMapping("/hello")
    public String hello (@RequestParam(name = "name", defaultValue = "World") String name){
        return String.format("Hello, %s",name);
    }

    @PostMapping("/newProduct")
    public ResponseEntity<Product> createProduct (@RequestBody ProductAPI productAPI){
        if (productAPI.getPrice()<0){
            throw new IllegalArgumentException("Price must be positive !");
        }
        return new ResponseEntity<>(catalogService.createProduct(productAPI), HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> showAllProducts(){
        List<Product> list = (List<Product>) catalogService.showAllProducts();
        if(list.size()!=0){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }
    @GetMapping("/product/{idProduct}")
    public ResponseEntity <Product> showOneProduct(@PathVariable("idProduct") Integer idProduct){
        Product product = catalogService.showOneProduct(idProduct);
        if(product!= null) {

            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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


   //////////////////////////////////////////////////////////////////////::

    @GetMapping("/categories")
    public ResponseEntity <List<Category>> showAllCategories(){
        List<Category> categoryList  = catalogService.showAllCategories();
        if(categoryList!= null && !categoryList.isEmpty()) {

            return new ResponseEntity<>(categoryList,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/catalogs")
    public ResponseEntity <List<Catalog>> showAllCatalogs(){
        List<Catalog> catalogList  = catalogService.showAllCatalogs();
        if(catalogList!= null && !catalogList.isEmpty()) {

            return new ResponseEntity<>(catalogList,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/stocks")
    public ResponseEntity <List<Stock>> showAllStocks(){
        List<Stock> stockList  = catalogService.showAllStocks();
        if(stockList!= null && !stockList.isEmpty()) {

            return new ResponseEntity<>(stockList,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/category/{idCategory}")
    public ResponseEntity <Category> showOneCategory(@PathVariable("idCategory") Integer idCategory){
        Category category = catalogService.showOneCategory(idCategory);
        if(category!= null) {

            return new ResponseEntity<>(category,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/cart/{idCart}")
    public ResponseEntity <Cart> showOneCart(@PathVariable("idCart") Integer idCart){
        Cart cart= catalogService.showOneCart(idCart);
        if(cart!= null) {
            return new ResponseEntity<>(cart,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/stock/{idStock}")
    public ResponseEntity <Stock> showOneStock(@PathVariable("idStock") Integer idStock){
        Stock stock= catalogService.showOneStock(idStock);
        if(stock!= null) {
            return new ResponseEntity<>(stock,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/catalog/{idCatalog}")
    public ResponseEntity <Catalog> showOneCatalog(@PathVariable("idCatalog") Integer idCatalog){
        Catalog catalog= catalogService.showOneCatalog(idCatalog);
        if(catalog!= null) {
            return new ResponseEntity<>(catalog,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }








    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }



}
