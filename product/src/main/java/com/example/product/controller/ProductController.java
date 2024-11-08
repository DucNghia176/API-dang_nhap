package com.example.product.controller;

import com.example.product.dto.request.ProductCreateRequest;
import com.example.product.entity.Product;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public Product createProduct(@RequestBody ProductCreateRequest request){

        return productService.createProduct(request);
    }

    @PutMapping("/{productID}")
    public Product uppdateProduct(@PathVariable Long productID, @RequestBody ProductCreateRequest request){
        return productService.uppdateProduct(productID, request);
    }

    @DeleteMapping("/{userId}")
    public String deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return "User has been deleted";
    }

}
