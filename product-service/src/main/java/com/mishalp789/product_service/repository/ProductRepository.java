package com.mishalp789.product_service.repository;

import com.mishalp789.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProductRepository extends JpaRepository<Product, Long> {

}
