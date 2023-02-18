package com.example.ecommerce2.controller;

import com.example.ecommerce2.model.Cart;
import com.example.ecommerce2.model.Product;
import com.example.ecommerce2.modelAPI.ProductAPI;
import com.example.ecommerce2.service.CatalogService;
import com.example.ecommerce2.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderControllerIntTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private JdbcTemplate jdbcTemplate;




    @BeforeEach
    void setUp(){
        jdbcTemplate.execute("DELETE FROM cart_product");
        jdbcTemplate.execute("DELETE FROM category_product");
        jdbcTemplate.execute("DELETE FROM category_sub_category_list");
        jdbcTemplate.execute("DELETE FROM stock_product");
        jdbcTemplate.execute("DELETE FROM stock_product");


        jdbcTemplate.execute("DELETE FROM customerorder_product_bought_list");
        jdbcTemplate.execute("DELETE FROM product_bought");
        jdbcTemplate.execute("DELETE FROM customerorder");


        jdbcTemplate.execute("DELETE FROM stock");
        jdbcTemplate.execute("DELETE FROM product");
        jdbcTemplate.execute("DELETE FROM category");
        jdbcTemplate.execute("DELETE FROM cart");




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

        catalogService.addProductTOStock(prod1.getId(),1);
        catalogService.addProductTOStock(prod2.getId(),100);

    }
    private byte[] toJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }


    @Test
    void testAddItemToCartOK() throws Exception {
        List<Product> list = catalogService.showAllProducts();

        if (!list.isEmpty()){
            Integer idProduct = list.get(0).getId();
            Integer idCart = 1;
            mockMvc.perform(put("/order/AddItemCart/"+idCart) // this cart doesn't exist but it will be created
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(idProduct)))
                    .andExpect(status().isCreated());
        }
    }

    @Test
    void TestAddItemToCartKO() throws Exception {
        List<Product> list = catalogService.showAllProducts();

        if (!list.isEmpty()){
            Integer idProduct = list.get(3).getId() + 1; // this Id doesn't exist in the DB, this will throw an exception
            mockMvc.perform(put("/AddItemCart/"+idProduct) // this cart doesn't exist but it will be created
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(idProduct)))
                    .andExpect(status().isNotFound());
        }
    }

    @Test
    void testGetTotalCart() throws Exception {
        List<Product> productList = catalogService.showAllProducts();
        if (!productList.isEmpty() && productList!=null){
            Cart cart1 = catalogService.createCart(Arrays.asList(productList.get(0).getId(),productList.get(1).getId()));// cart with prod1 ans prod2
            Integer idCart = cart1.getId();
            mockMvc.perform(get("/order/totalCart/"+idCart))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(30));
        }
    }
    @Test
    void testPayOK() throws Exception {
        List<Product> productList = catalogService.showAllProducts();
        if (!productList.isEmpty() && productList!=null){
            Cart cart1 = catalogService.createCart(Arrays.asList(productList.get(0).getId(),productList.get(1).getId()));// cart with prod1 ans prod2
            Integer idCart = cart1.getId();
            mockMvc.perform(get("/order/pay/"+idCart))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(30));
        }
    }

    @Test
    void testPayCartEmpty() throws Exception {



        Cart cart1 = catalogService.createCart(Arrays.asList());// cart with 0 products
        Integer idCart = cart1.getId();
        mockMvc.perform(get("/order/pay/"+idCart))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(0.0));

    }
    @Test
    void testPayCartNotFound() throws Exception {



        mockMvc.perform(get("/order/pay/1"))  // this cart doesn't exist
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value(-1.0));
    }

}