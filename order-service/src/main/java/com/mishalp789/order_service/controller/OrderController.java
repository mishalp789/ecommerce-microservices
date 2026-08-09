package com.mishalp789.order_service.controller;

import com.mishalp789.order_service.dto.*;
import com.mishalp789.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse createOrder(
            @Valid @RequestBody OrderRequest request){

        return orderService.createOrder(request);
    }

    @GetMapping
    public List<OrderResponse> getAllOrders(){

        return orderService.getAllOrders();
    }

}