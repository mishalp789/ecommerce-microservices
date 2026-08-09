package com.mishalp789.order_service.service;

import com.mishalp789.order_service.client.ProductClient;
import com.mishalp789.order_service.dto.OrderRequest;
import com.mishalp789.order_service.dto.OrderResponse;
import com.mishalp789.order_service.dto.ProductResponse;
import com.mishalp789.order_service.entity.Order;
import com.mishalp789.order_service.entity.OrderStatus;
import com.mishalp789.order_service.exception.ProductServiceException;
import com.mishalp789.order_service.mapper.OrderMapper;
import com.mishalp789.order_service.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductClient productClient;
    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl service;

    private OrderRequest request;
    private ProductResponse productResponse;
    private Order order;
    private OrderResponse orderResponse;

    @BeforeEach
    void setup() {

        request = OrderRequest.builder()
                .productId(1L)
                .quantity(2)
                .build();

        productResponse = ProductResponse.builder()
                .id(1L)
                .name("MacBook")
                .price(BigDecimal.valueOf(120000))
                .quantity(10)
                .build();

        order = Order.builder()
                .id(1L)
                .productId(1L)
                .quantity(2)
                .totalPrice(BigDecimal.valueOf(240000))
                .status(OrderStatus.CONFIRMED)
                .build();

        orderResponse = OrderResponse.builder()
                .id(1L)
                .productId(1L)
                .quantity(2)
                .totalPrice(BigDecimal.valueOf(240000))
                .status(OrderStatus.CONFIRMED)
                .build();
    }

    @Test
    void shouldCreateOrder() {

        when(productClient.getProduct(1L))
                .thenReturn(productResponse);

        when(productClient.decreaseStock(1L, 2))
                .thenReturn(productResponse);

        when(orderRepository.save(any(Order.class)))
                .thenReturn(order);

        when(orderMapper.toResponse(order))
                .thenReturn(orderResponse);

        OrderResponse result = service.createOrder(request);

        assertNotNull(result);
        assertEquals(OrderStatus.CONFIRMED, result.getStatus());
        assertEquals(BigDecimal.valueOf(240000), result.getTotalPrice());

        verify(productClient).getProduct(1L);
        verify(productClient).decreaseStock(1L, 2);
        verify(orderRepository).save(any(Order.class));
        verify(orderMapper).toResponse(order);
    }

    @Test
    void shouldThrowProductServiceExceptionWhenProductClientFails() {

        when(productClient.getProduct(1L))
                .thenThrow(new RuntimeException("Service unavailable"));

        assertThrows(
                ProductServiceException.class,
                () -> service.createOrder(request)
        );

        verify(productClient).getProduct(1L);
        verify(orderRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenDecreaseStockFails() {

        when(productClient.getProduct(1L))
                .thenReturn(productResponse);

        doThrow(new RuntimeException("Insufficient stock"))
                .when(productClient)
                .decreaseStock(1L, 2);

        assertThrows(
                ProductServiceException.class,
                () -> service.createOrder(request)
        );

        verify(productClient).getProduct(1L);
        verify(productClient).decreaseStock(1L, 2);
        verify(orderRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllOrders() {

        Order order2 = Order.builder()
                .id(2L)
                .productId(2L)
                .quantity(1)
                .totalPrice(BigDecimal.valueOf(50000))
                .status(OrderStatus.CONFIRMED)
                .build();

        OrderResponse response2 = OrderResponse.builder()
                .id(2L)
                .productId(2L)
                .quantity(1)
                .totalPrice(BigDecimal.valueOf(50000))
                .status(OrderStatus.CONFIRMED)
                .build();

        when(orderRepository.findAll())
                .thenReturn(List.of(order, order2));

        when(orderMapper.toResponse(order))
                .thenReturn(orderResponse);

        when(orderMapper.toResponse(order2))
                .thenReturn(response2);

        List<OrderResponse> result = service.getAllOrders();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getProductId());

        verify(orderRepository).findAll();
    }




}
