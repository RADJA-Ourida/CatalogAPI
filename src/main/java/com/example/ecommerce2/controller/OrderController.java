package com.example.ecommerce2.controller;


import com.example.ecommerce2.exception.ProductNotFound;
import com.example.ecommerce2.model.Cart;
import com.example.ecommerce2.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

        Cart cart = orderService.addItemToCart(idCart,idProduct);
        if (cart!=null){
            return  new ResponseEntity<>( cart,HttpStatus.CREATED);
        }
        return  new ResponseEntity<>( HttpStatus.NOT_FOUND);


    }

    @GetMapping("/totalCart/{idCart}")
    public ResponseEntity<Double> getTotalCart(@PathVariable("idCart") Integer idCart){
        return new ResponseEntity<>(orderService.calculateTotalCart(idCart),HttpStatus.OK);
    }


    @GetMapping("/pay/{idCart}")
    public ResponseEntity<Double> pay (@PathVariable("idCart") Integer idCart){
        Double priceToPay = orderService.pay(idCart);

        if (priceToPay == -1){
            return new ResponseEntity<>(priceToPay,HttpStatus.NOT_FOUND); // cart not found

        }
        else {
            return new ResponseEntity<>(priceToPay,HttpStatus.OK);
        }


    }


}
