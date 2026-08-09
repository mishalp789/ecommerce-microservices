package com.mishalp789.order_service.controller;

import com.mishalp789.order_service.dto.OrderRequest;
import com.mishalp789.order_service.dto.OrderResponse;
import com.mishalp789.order_service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(
        name = "Order Management",
        description = "APIs for creating and managing customer orders."
)
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(
            summary = "Create a new order",
            description = "Creates a new customer order using the provided order details and returns the created order information."
    )
    public OrderResponse createOrder(
            @Valid @RequestBody OrderRequest request) {

        return orderService.createOrder(request);
    }

    @GetMapping
    @Operation(
            summary = "Retrieve all orders",
            description = "Fetches a list of all customer orders available in the system."
    )
    public List<OrderResponse> getAllOrders() {

        return orderService.getAllOrders();
    }
}