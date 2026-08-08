package com.mishalp789.order_service.mapper;

import com.mishalp789.order_service.dto.OrderResponse;
import com.mishalp789.order_service.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .build();
    }


}
