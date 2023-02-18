package com.example.ecommerce2.service;


import com.example.ecommerce2.exception.ProductNotFound;
import com.example.ecommerce2.model.*;
import com.example.ecommerce2.modelAPI.*;
import com.example.ecommerce2.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CatalogService {



    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final CartRepository cartRepository;

    private final CatalogRepository catalogRepository;

    private final StockRepository stockRepository;



    public Product createProduct(ProductAPI productAPI){
        Product productToSave = new Product(productAPI.getPrice(),productAPI.getName());
        return productRepository.save(productToSave);
    }

    public Stock addProductTOStock (Integer idProduct, Integer qntStock){
        Stock stock1 = stockRepository.findStockByProductId(idProduct);
        if (stock1 != null){
            stock1.setQuantityStock(stock1.getQuantityStock() + qntStock);
            return stockRepository.save(stock1);
        }
        Product product = productRepository.findProductById(idProduct);
        if (product != null){
            Stock newStock = new Stock(qntStock,product);
            return stockRepository.save(newStock);
        }
        throw new ProductNotFound("Product not found!!!");
    }

    public Cart createCart(List<Integer> idProductList){
        List <Product> productListToSave  = new ArrayList<>();
        Cart cartToSave = new Cart();

        if (idProductList != null && !idProductList.isEmpty()){
            productListToSave = productRepository.findAllById(idProductList);
        } // else: if ProduitList is null we don't have anything to do
        cartToSave.setProductList(productListToSave);
        return cartRepository.save(cartToSave);
    }



    public Category createCategory(CategoryAPI categoryRequestAPI) {
        Category categoryToSave  = new Category();
        categoryToSave.setName(categoryRequestAPI.getName());
        categoryToSave.setDescription(categoryRequestAPI.getDescription());
        if (categoryRequestAPI.getIdSubCategoryList() != null && !categoryRequestAPI.getIdSubCategoryList().isEmpty() ){ // Find the subCatigories by the given Id;
            List<Category> subCategoryListToSave = categoryRepository.findAllById(categoryRequestAPI.getIdSubCategoryList() );
            categoryToSave.setSubCategoryList(subCategoryListToSave);
        }
        if (categoryRequestAPI.getIdProductList() != null  && ! categoryRequestAPI.getIdProductList().isEmpty()){ //Find the Items  by the given Id;
            List<Product> productListToSave = productRepository.findAllById(categoryRequestAPI.getIdProductList() );
            categoryToSave.setProductList(productListToSave);
        }

        return categoryRepository.save(categoryToSave);
    }

    public Catalog createCatalog(CatalogAPI catalogAPI){
        Catalog catalogToSave = new Catalog();
        List<Category> categoryList = new ArrayList<>();
        if(catalogAPI.getIdCategoryList()!=null && !catalogAPI.getIdCategoryList().isEmpty()){
             categoryList = categoryRepository.findAllById(catalogAPI.getIdCategoryList());

        }
        catalogToSave.setCategoryList(categoryList);

        return catalogRepository.save(catalogToSave);
    }



    public List<Category> showAllCategories(){
        return categoryRepository.findAll();
    }

    public List<Product> showAllProducts(){
        return productRepository.findAll();
    }
    public List<Catalog>  showAllCatalogs(){
        return catalogRepository.findAll();
    }
    public  List<Stock> showAllStocks(){return stockRepository.findAll();}

    public Product showOneProduct(Integer id){
        return productRepository.findProductById(id);
    }
    public Category showOneCategory(Integer id){
        return categoryRepository.findCategoryById(id);

    }
    public Cart showOneCart(Integer id){
        return cartRepository.findCartById(id);
    }
    public Stock showOneStock(Integer id){
        return  stockRepository.findStockById(id);

    }
    public Catalog showOneCatalog(Integer id){
        return catalogRepository.findCatalogById(id);
    }

    public void deleteOneProduct(Integer id){
        productRepository.deleteById(id);
    }
    public void deleteOneCart(Integer id){
        cartRepository.deleteById(id);
    }
    public void deleteOneCategory(Integer id){
        categoryRepository.deleteById(id);
    }
    public void deleteOneCatalog(Integer id){
        catalogRepository.deleteById(id);
    }



}
