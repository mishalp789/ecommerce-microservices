package com.mishalp789.product_service.service;

import com.mishalp789.product_service.dto.ProductRequest;
import com.mishalp789.product_service.dto.ProductResponse;
import com.mishalp789.product_service.entity.Product;
import com.mishalp789.product_service.exception.InsufficientStockException;
import com.mishalp789.product_service.exception.ProductNotFoundException;
import com.mishalp789.product_service.mapper.ProductMapper;
import com.mishalp789.product_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> getAllProducts() {

        List<Product> products= productRepository.findAll();
        return products.stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException(id));

        return productMapper.toResponse(product);
    }
    @Transactional
    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = productMapper.toEntity(request);

        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

    @Transactional
    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException(id));

        productMapper.updateEntity(existingProduct,request);
        Product saved = productRepository.save(existingProduct);

        return productMapper.toResponse(saved);


    }

    @Transactional
    @Override
    public String deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(product);

        return "Product deleted successfully";
    }

    @Override
    @Transactional
    public ProductResponse decreaseStock(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException(id));

        if(product.getQuantity()<quantity){
            throw new InsufficientStockException();
        }

        product.setQuantity(product.getQuantity()-quantity);

        Product saved = productRepository.save(product);

        return productMapper.toResponse(saved);
    }


}
