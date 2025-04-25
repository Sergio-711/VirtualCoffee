package com.VirtualCoffee.orders.service;

import com.VirtualCoffee.orders.controller.request.OrderRequest;
import com.VirtualCoffee.orders.exception.BeverageUnavailableException;
import com.VirtualCoffee.orders.model.Order;
import com.VirtualCoffee.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BeverageClient beverageClient;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(OrderRequest order){
        System.out.println("Creating order: " + order.getName() + ", size: " + order.getSize());
        if(order.getName() == null || order.getName().isEmpty()) {
            System.out.println("Name cannot be null or empty");
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (beverageClient.isBeverageAvailable(order.getName(), order.getSize())) {
            Order newOrder = new Order(null, order.getName(), order.getSize());
            return orderRepository.save(newOrder);
        } else {
            System.out.println("Beverage is unavailable");
            throw new BeverageUnavailableException();
        }
    }
}
