package com.example.product.service;

import com.example.product.dto.request.ProductCreateRequest;
import com.example.product.entity.Product;
import com.example.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(ProductCreateRequest request){
        if (productRepository.existsByProductName(request.getProductName()))
            throw new IllegalArgumentException("Đã tồn tại sản phẩm");

        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(request.getCategory());

        return productRepository.save(product);
    }

    public Product uppdateProduct(Long productID, ProductCreateRequest request){
        Product product = productRepository.findById(productID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(request.getCategory());

        return productRepository.save(product);
    }

    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }


}
