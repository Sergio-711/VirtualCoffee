package com.VirtualCoffee.orders.controller;

import com.VirtualCoffee.orders.controller.request.OrderRequest;
import com.VirtualCoffee.orders.exception.BeverageUnavailableException;
import com.VirtualCoffee.orders.model.Order;
import com.VirtualCoffee.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrder() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

}
