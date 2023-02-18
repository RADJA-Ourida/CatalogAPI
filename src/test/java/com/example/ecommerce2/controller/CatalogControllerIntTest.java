package com.example.ecommerce2.controller;

import com.example.ecommerce2.model.Product;
import com.example.ecommerce2.modelAPI.ProductAPI;
import com.example.ecommerce2.service.CatalogService;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CatalogControllerIntTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setUp(){


        jdbcTemplate.execute("DELETE FROM cart_product");
        jdbcTemplate.execute("DELETE FROM category_product");
        jdbcTemplate.execute("DELETE FROM category_sub_category_list");
        jdbcTemplate.execute("DELETE FROM stock_product");
        jdbcTemplate.execute("DELETE FROM stock");
        jdbcTemplate.execute("DELETE FROM product");
        jdbcTemplate.execute("DELETE FROM category");
        jdbcTemplate.execute("DELETE FROM cart");


        // 1. Insert 4 Products
        ProductAPI productAPI1 = new ProductAPI(10.0,"Prod1");
        ProductAPI productAPI2 = new ProductAPI(20.0,"Prod2");
        ProductAPI productAPI3 = new ProductAPI(10.0,"Prod3");
        ProductAPI productAPI4 = new ProductAPI(10.0,"Prod4");

        Product prod1 = catalogService.createProduct(productAPI1);
        Product prod2 = catalogService.createProduct(productAPI2);
        Product prod3 = catalogService.createProduct(productAPI3);
        Product prod4 = catalogService.createProduct(productAPI4);

        // Add products to stock
        catalogService.addProductTOStock(prod1.getId(),100);
        catalogService.addProductTOStock(prod2.getId(),100);

        //3. Insert Carts and add items to those carts
        //Cart cart1 = catalogService.createCart(Arrays.asList(prod1.getId(),prod2.getId()));// cart with prod1 ans prod2
    }
    private byte[] toJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }

    @Test
    void testHelloDani() throws Exception {
        RequestBuilder requestBuilder = get("/api/hello?name=Dani");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertEquals("Hello, Dani",result.getResponse().getContentAsString());
    }

    @Test
    public void testCreateProduct() throws Exception {
        ProductAPI productAPI1 = new ProductAPI(-5.0,"Prod5");
        mockMvc.perform(post("/api/newProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(productAPI1)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Price must be positive !"));
    }

    @Test
    public void testCreateProductOK() throws Exception {
        ProductAPI productAPI1 = new ProductAPI(50.0,"Prod5");
        mockMvc.perform(post("/api/newProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(productAPI1)))
                .andExpect(status().isCreated());
    }

    @Test
    void testShowOneProductOK() throws Exception {
        List<Product> list = new ArrayList<>();
        list = catalogService.showAllProducts();
        if (!list.isEmpty()){
            Integer id = list.get(0).getId();
            //RequestBuilder requestBuilder = get("/api/product/id");
            mockMvc.perform(get("/api/product/"+id)).andExpect(status().isOk());
        }
    }
    @Test
    void testShowOneProductKO() throws Exception {
        mockMvc.perform(get("/api/product/0"))
                .andExpect(status().isNotFound());
    }
    @Test
    void testShowAllProductOK() throws Exception {

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Prod1"))
                .andExpect(jsonPath("$",hasSize(4)));
    }


    @Test
    void testAddProductToStockOK() throws  Exception{
        List<Product> list =  catalogService.showAllProducts();

        if (!list.isEmpty() && list!= null){
            int quantity = 20;
            Integer idProduct = list.get(2).getId();//this is prod3
            mockMvc.perform(post("/api/addProductStock/"+idProduct)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(quantity)))
                    .andExpect((status().isCreated()));
        }
    }


    @Test
    void testAddProductToStockQuantityNegative() throws  Exception{
        List<Product> list = new ArrayList<>();
        list = catalogService.showAllProducts();
        if (!list.isEmpty()){
            int quantity = -20;
            Integer idProduct = list.get(2).getId();//this is prod3
            mockMvc.perform(post("/api/addProductStock/"+idProduct)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(quantity)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("Quantity must be positive"));
        }
    }

}