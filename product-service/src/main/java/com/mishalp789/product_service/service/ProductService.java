package com.mishalp789.product_service.service;

import com.mishalp789.product_service.dto.ProductRequest;
import com.mishalp789.product_service.dto.ProductResponse;
import com.mishalp789.product_service.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(Long id,ProductRequest request);
    String deleteProduct(Long id);
    ProductResponse decreaseStock(Long id,Integer quantity);
}
