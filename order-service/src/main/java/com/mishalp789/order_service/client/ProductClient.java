package com.mishalp789.order_service.client;

import com.mishalp789.order_service.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductResponse getProduct(@PathVariable Long id);

    @PutMapping("/products/{id}/decrease-stock")
    ProductResponse decreaseStock(
            @PathVariable Long id,
            @RequestParam Integer quantity);

}