package com.mishalp789.product_service;

import com.mishalp789.product_service.dto.ProductRequest;
import com.mishalp789.product_service.dto.ProductResponse;
import com.mishalp789.product_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(
        name = "Product Management",
        description = "APIs for managing products and inventory."
)
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(
            summary = "Create a new product",
            description = "Creates a new product with the provided details and stores it in the product catalog."
    )
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping
    @Operation(
            summary = "Retrieve all products",
            description = "Fetches a list of all products available in the product catalog."
    )
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve a product by ID",
            description = "Fetches the details of a specific product using its unique identifier."
    )
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a product",
            description = "Updates the details of an existing product identified by its unique ID."
    )
    public ProductResponse updateProductById(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a product",
            description = "Removes the specified product from the product catalog using its unique ID."
    )
    public String deleteProductById(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @PutMapping("/{id}/decrease-stock")
    @Operation(
            summary = "Decrease product stock",
            description = "Reduces the available stock quantity of the specified product after validating the requested quantity."
    )
    public ProductResponse decreaseStock(
            @PathVariable Long id,
            @RequestParam Integer quantity
    ) {
        return productService.decreaseStock(id, quantity);
    }
}