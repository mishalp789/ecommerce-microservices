package com.mishalp789.order_service.listener;

import com.mishalp789.order_service.config.RabbitMQConfig;
import com.mishalp789.order_service.dto.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEventListener {

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void receive(OrderCreatedEvent event) {

        log.info("========== ORDER CREATED ==========");
        log.info("Order ID     : {}", event.getOrderId());
        log.info("Product ID   : {}", event.getProductId());
        log.info("Quantity     : {}", event.getQuantity());
        log.info("Total Price  : {}", event.getTotalPrice());
        log.info("===================================");

    }
}