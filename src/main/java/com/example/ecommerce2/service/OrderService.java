package com.example.ecommerce2.service;

import com.example.ecommerce2.exception.ProductNotFound;
import com.example.ecommerce2.model.*;
import com.example.ecommerce2.model.ProductBought;
import com.example.ecommerce2.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CartRepository cartRepository;
    private final CatalogRepository catalogRepository;

    private final StockRepository stockRepository;

    private final OrderRepository orderRepository;

    private final ProductBoughtRepository productBoughtRepository;

    public Cart addItemToCart(Integer idCart, Integer idProduct) {


        Product productRequest = productRepository.findProductById(idProduct);
        if (productRequest != null) {// check if this item exists
            Cart cartRequest = cartRepository.findCartById(idCart);

            if (cartRequest != null) {// check if this cart exists
                cartRequest.getProductList().add(productRequest);
                return cartRepository.save(cartRequest);
            } //else : create a new cart
            Cart cartToSave = new Cart();
            cartToSave.setProductList(new ArrayList<>());
            cartToSave.getProductList().add(productRequest);
            return cartRepository.save(cartToSave);
        }
        throw new ProductNotFound("Product does not exist!!!");
    }


    public Double calculateTotalCart(Integer idCart) {
        Cart cart = cartRepository.findCartById(idCart);
        Double total = 0.0;
        if (cart != null) {
            List<Product> productList = cart.getProductList();
            if (productList != null && !productList.isEmpty()) {

                for (int i = 0; i < productList.size(); i++) {
                    total += productList.get(i).getPrice();

                }
            }
        }
        return total;
    }




    @Transactional
    public Double pay(Integer idCart) {

        Cart cart = cartRepository.findCartById(idCart);
        Double totalCart = 0.0;

        if (cart != null) {
            List<Product> productList = cart.getProductList();
            if (productList != null && !productList.isEmpty()) {

                // 1- convert the list into a Map
                Map<Integer, Integer> productMapVar = convertListProductToMap(productList);////HashMap <IdProduit,Quantity>
                //2-Update the quantities of the map depending on the stock
                productMapVar = updateMapProduct(productMapVar);

                List<ProductBought> productBoughtListVide = new ArrayList<>();

                Order order1 = new Order(productBoughtListVide);
                //3- calculate the total of the cart and update the stock
                for (Map.Entry<Integer, Integer> entry : productMapVar.entrySet()) {//HashMap <IdProduit,Quantity>

                    Product p = productRepository.findProductById(entry.getKey());

                    Double price = p.getPrice();
                    totalCart += price * entry.getValue();
                    updateStock(entry.getKey(), entry.getKey());
                    order1.getProductBoughtList().add( createInstanceProductBought(p,entry.getValue()) );
                }
                //4- Save the order (traceability)
                 saveOrderx(order1, totalCart);
                //5- vider le panier
                cart.getProductList().clear();
                cartRepository.save(cart);

                return totalCart;//"Payment Done. You'll receive your order. ";

            }// else if the cart is empty
            return totalCart; //totalCart === 0.0

        } // Else if the cart doesn't exist
        return -1.0;
    }

    //---------------------------------------------------------------------------------------------

    public Map<Integer,Integer> convertListProductToMap(List<Product> productList){
        Map<Integer, Integer> productMapVar = productList.stream()
                .collect(Collectors.toMap(Product::getId, i->1, Integer::sum));
        return productMapVar;
    }


    public Map<Integer,Integer> updateMapProduct(Map<Integer,Integer> productMapVar){ //Map<ProductId, Quantity>

        for (Map.Entry<Integer, Integer> entry : productMapVar.entrySet()) {
            Stock stock = stockRepository.findStockByProductId(entry.getKey());
            if (stock != null) { // check if product is in stock
                if (stock.getQuantityStock() < entry.getValue()) { // we attribute the quantity that we have to the cart
                    productMapVar.replace(entry.getKey(), entry.getValue(),
                            stock.getQuantityStock());
                } //Else we don't do anything
            } else { // the product is not in the stock
                productMapVar.remove(entry.getKey());
            }
        }
        return productMapVar;
    }

    public void updateStock(Integer idProduct, Integer quantityCart){
        Stock stock = stockRepository.findStockByProductId(idProduct);
        Integer newQuantityStock = stock.getQuantityStock() - quantityCart;
        if (newQuantityStock == 0) {// delete the item from the stock if its quantity is zero
            stockRepository.delete(stock);
        } else { // else update the stock of this item
            stock.setQuantityStock(newQuantityStock);
            stockRepository.save(stock);
        }
    }

    public ProductBought createInstanceProductBought(Product p, Integer quantity){

        ProductBought pBought = new ProductBought();
        pBought.setProduct(p);
        pBought.setQuantityBought(quantity);
        return productBoughtRepository.save(pBought);
    }

    public void saveOrderx(Order order1, Double totalCart){
        Date d = new Date();
        order1.setOrderDate(d);
        order1.setTotalOrder(totalCart);
         orderRepository.save(order1);

    }


    public Map<Integer,Integer> convertListProductToMap2(List<Product> productList){//Map<IdProduit,Qantity>
        Map<Integer, Integer> productMapVar = new HashMap<>();//HashMap <IdProduit,Quantity>

        for (int i = 0; i < productList.size(); i++) {
            Integer idVar = productList.get(i).getId();
            if (productMapVar.containsKey(idVar)) { // if the product appears more than one time in the list
                productMapVar.replace(idVar, productMapVar.get(idVar).intValue(),
                        productMapVar.get(idVar).intValue() + 1);
            }
            else productMapVar.put(idVar, 1); // if the product appears for the first time in the list
        }
        return productMapVar;
    }











}
