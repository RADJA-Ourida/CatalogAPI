package com.example.ecommerce2.service;

import com.example.ecommerce2.model.*;
import com.example.ecommerce2.modelAPI.ProductAPI;
import com.example.ecommerce2.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CatalogRepository catalogRepository;
    @Mock
    private StockRepository stockRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductBoughtRepository productBoughtRepository;

    private OrderService orderService;
    private CatalogService catalogService;


    @BeforeEach
    void setUp() {
        orderService = new OrderService(productRepository, categoryRepository, cartRepository,
                catalogRepository, stockRepository, orderRepository, productBoughtRepository);

        catalogService = new CatalogService(productRepository, categoryRepository, cartRepository,
                catalogRepository, stockRepository);

        // Products
        Product product1 = new Product(10.0, "prod1");

        when(productRepository.findProductById(1)).thenReturn(product1);

        //Cart
        ArrayList<Product> produitList = new ArrayList<>();

        Cart cart = new Cart(produitList);

        when(cartRepository.findCartById(1)).thenReturn(cart);

        ArrayList<Product> productList2 = new ArrayList<>();
        productList2.add(product1);
        productList2.add(product1);
        Cart cart2 = new Cart(productList2);
        when(cartRepository.findCartById(2)).thenReturn(cart2);


    }


    @Test
    void addItemCartCalculateTotalCart() {
        //Given
        Product product1 = new Product(10.0, "prod1");

        //when
        orderService.addItemToCart(1, 1);
        //then
        ArgumentCaptor<Cart> cartArgumentCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(cartRepository).save(cartArgumentCaptor.capture());//Make sure that we invoke the function save()
        Cart capturedCart = cartArgumentCaptor.getValue();
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        assertEquals(capturedCart.getProductList().get(0).getName(), productList.get(0).getName());


        //when
        Double x = orderService.calculateTotalCart(2);

        //then
        assertEquals(x, 20.0);
    }
}