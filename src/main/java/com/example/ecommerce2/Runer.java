package com.example.ecommerce2;

import com.example.ecommerce2.model.*;
import com.example.ecommerce2.modelAPI.CategoryAPI;
import com.example.ecommerce2.modelAPI.ProductAPI;
import com.example.ecommerce2.service.CatalogService;
import com.example.ecommerce2.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@AllArgsConstructor
public class Runer implements CommandLineRunner {
    private final CatalogService catalogService;
    private final OrderService orderService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Hello from CommandeLineRunner");

       // 1. Insert 4 Products

        ProductAPI productAPI1 = new ProductAPI(10.0,"Prod1");
        ProductAPI productAPI2 = new ProductAPI(20.0,"Prod2");
        ProductAPI productAPI3 = new ProductAPI(30.0,"Prod3");
        ProductAPI productAPI4 = new ProductAPI(40.0,"Prod4");
        Product prod1 = catalogService.createProduct(productAPI1);
        Product prod2 = catalogService.createProduct(productAPI2);
        Product prod3 = catalogService.createProduct(productAPI3);
        Product prod4 = catalogService.createProduct(productAPI4);
        // Add products to stock

        catalogService.addProductTOStock(prod1.getId(),100);
        catalogService.addProductTOStock(prod2.getId(),100);
        catalogService.addProductTOStock(prod3.getId(),100);
        catalogService.addProductTOStock(prod4.getId(),100);

        // 2. Insert Categories
        CategoryAPI categoryAPI1 = new CategoryAPI("A","This is category A",
                Arrays.asList(prod1.getId(),prod2.getId()),null);
        Category categoryA = catalogService.createCategory(categoryAPI1);


        CategoryAPI categoryAPI2 = new CategoryAPI("B","This is category B",
                null,Arrays.asList(categoryA.getId())); // subCategory = A
        catalogService.createCategory(categoryAPI2);


        CategoryAPI categoryAPI3 = new CategoryAPI("C","This is category C",
                Arrays.asList(prod1.getId(),prod4.getId()),null);
        catalogService.createCategory(categoryAPI3);


        //3. Insert Carts and add items to those carts
        Cart cart1 = catalogService.createCart(Arrays.asList(prod1.getId(),prod2.getId()));// cart with prod1 ans prod2



        /*this part is optioannal but it is not working
       Double total = orderService.calculateTotalCart(cart1.getId());

        System.out.println("expected Total  ::  30 ");
        System.out.println("The actual Total ::"+total);

         */


    }
}
