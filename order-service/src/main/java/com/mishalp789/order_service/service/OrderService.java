package com.mishalp789.order_service.service;

import com.mishalp789.order_service.dto.OrderRequest;
import com.mishalp789.order_service.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);
    List<OrderResponse> getAllOrders();
}
