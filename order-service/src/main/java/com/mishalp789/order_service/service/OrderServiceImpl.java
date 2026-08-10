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
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final OrderMapper orderMapper;

    @CircuitBreaker(
            name = "productService",
            fallbackMethod = "createOrderFallback")
    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {

        ProductResponse product =
                productClient.getProduct(request.getProductId());

        productClient.decreaseStock(
                request.getProductId(),
                request.getQuantity());

        BigDecimal total =
                product.getPrice()
                        .multiply(BigDecimal.valueOf(request.getQuantity()));

        Order order = Order.builder()
                .productId(product.getId())
                .quantity(request.getQuantity())
                .totalPrice(total)
                .status(OrderStatus.CONFIRMED)
                .build();

        Order saved = orderRepository.save(order);

        return orderMapper.toResponse(saved);

    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    private OrderResponse createOrderFallback(OrderRequest request,Exception ex){
        throw new ProductServiceException(
                "Product Service is currently unavailable. Please try again later."
        );
    }
}
