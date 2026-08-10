package com.mishalp789.order_service.service;


import com.mishalp789.order_service.config.RabbitMQConfig;
import com.mishalp789.order_service.dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(OrderCreatedEvent event){
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_QUEUE,
                event
        );
    }
}
