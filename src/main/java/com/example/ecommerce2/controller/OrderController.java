package com.example.ecommerce2.controller;


import com.example.ecommerce2.model.Cart;
import com.example.ecommerce2.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;



    @PutMapping("AddItemCart/{idCart}")
    public ResponseEntity<Cart> addItemToCart(@PathVariable("idCart") Integer idCart,
                                                       @RequestBody Integer idProduct){
        return  new ResponseEntity<>(orderService.addItemToCart(idCart,idProduct), HttpStatus.CREATED);
    }

    @GetMapping("/totalCart/{idCart}")
    public ResponseEntity<Double> getTotalCart(@PathVariable("idCart") Integer idCart){
        return new ResponseEntity<>(orderService.calculateTotalCart(idCart),HttpStatus.OK);
    }


    @GetMapping("/pay/{idCart}")
    public ResponseEntity<Double> pay (@PathVariable("idCart") Integer idCart){
        return new ResponseEntity<>(orderService.pay(idCart),HttpStatus.OK);
    }


}
