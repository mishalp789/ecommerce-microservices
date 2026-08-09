package com.mishalp789.product_service.service;

import com.mishalp789.product_service.dto.ProductRequest;
import com.mishalp789.product_service.dto.ProductResponse;
import com.mishalp789.product_service.entity.Product;
import com.mishalp789.product_service.exception.InsufficientStockException;
import com.mishalp789.product_service.exception.ProductNotFoundException;
import com.mishalp789.product_service.mapper.ProductMapper;
import com.mishalp789.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    ProductRepository repository;

    @Mock
    ProductMapper mapper;

    @InjectMocks
    private ProductServiceImpl service;

    private Product product;
    private ProductRequest request;
    private ProductResponse response;

    @BeforeEach
    void setup(){

        product = Product.builder()
                .id(1L)
                .name("MacBook")
                .description("Apple Laptop")
                .price(BigDecimal.valueOf(120000))
                .quantity(10)
                .build();

        request = ProductRequest.builder()
                .name("MacBook")
                .description("Apple Laptop")
                .price(BigDecimal.valueOf(120000))
                .quantity(10)
                .build();

        response = ProductResponse.builder()
                .id(1L)
                .name("MacBook")
                .description("Apple Laptop")
                .price(BigDecimal.valueOf(120000))
                .quantity(10)
                .build();

    }

    @Test
    void shouldGetAllProducts(){
        Product product2 = Product.builder()
                .id(2L)
                .name("iPhone")
                .description("Apple Phone")
                .price(BigDecimal.valueOf(80000))
                .quantity(5)
                .build();

        ProductResponse response2 = ProductResponse.builder()
                .id(2L)
                .name("iPhone")
                .description("Apple Phone")
                .price(BigDecimal.valueOf(80000))
                .quantity(5)
                .build();

        when(repository.findAll())
                .thenReturn(List.of(product,product2));

        when(mapper.toResponse(product))
                .thenReturn(response);

        when(mapper.toResponse(product2))
                .thenReturn(response);

        List<ProductResponse> result = service.getAllProducts();

        assertEquals(2,result.size());
        assertEquals("MacBook",result.get(0).getName());

        verify(repository).findAll();
    }

    @Test
    void shouldGetProductById(){
        when(repository.findById(1L))
                .thenReturn(Optional.of(product));

        when(mapper.toResponse(product))
                .thenReturn(response);


        ProductResponse result = service.getProductById(1L);
        assertEquals("MacBook",result.getName());
        verify(repository).findById(1L);

    }

    @Test
    void shouldThrowExceptionWhenProductNotFound(){
        when(repository.findById(100L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                ()->service.getProductById(100L)
        );
    }

    @Test
    void shouldCreateProduct(){

        when(mapper.toEntity(request))
                .thenReturn(product);

        when(repository.save(product))
                .thenReturn(product);

        when(mapper.toResponse(product))
                .thenReturn(response);

        ProductResponse result = service.createProduct(request);

        assertNotNull(result);

        assertEquals("MacBook", result.getName());

        verify(repository).save(product);

    }


    @Test
    void shouldUpdateProduct() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(product));

        doNothing().when(mapper).updateEntity(product, request);

        when(repository.save(product))
                .thenReturn(product);

        when(mapper.toResponse(product))
                .thenReturn(response);

        ProductResponse result = service.updateProduct(1L, request);

        assertNotNull(result);
        assertEquals("MacBook", result.getName());

        verify(repository).findById(1L);
        verify(mapper).updateEntity(product, request);
        verify(repository).save(product);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingProduct() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> service.updateProduct(1L, request));

        verify(repository).findById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    void shouldDeleteProduct() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(product));

        String result = service.deleteProduct(1L);

        assertEquals("Product deleted successfully", result);

        verify(repository).findById(1L);
        verify(repository).delete(product);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingProduct() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> service.deleteProduct(1L));

        verify(repository).findById(1L);
        verify(repository, never()).delete(any());
    }

    @Test
    void shouldDecreaseStock() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(product));

        when(repository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Product updatedProduct = Product.builder()
                .id(1L)
                .name("MacBook")
                .description("Apple Laptop")
                .price(BigDecimal.valueOf(120000))
                .quantity(7)
                .build();

        ProductResponse updatedResponse = ProductResponse.builder()
                .id(1L)
                .name("MacBook")
                .description("Apple Laptop")
                .price(BigDecimal.valueOf(120000))
                .quantity(7)
                .build();

        when(mapper.toResponse(any(Product.class)))
                .thenReturn(updatedResponse);

        ProductResponse result = service.decreaseStock(1L, 3);

        assertEquals(7, result.getQuantity());

        verify(repository).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenStockIsInsufficient() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(InsufficientStockException.class,
                () -> service.decreaseStock(1L, 15));

        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenDecreasingStockForNonExistingProduct() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> service.decreaseStock(1L, 2));

        verify(repository, never()).save(any());
    }





}
