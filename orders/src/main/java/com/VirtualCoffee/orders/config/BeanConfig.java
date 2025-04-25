package com.VirtualCoffee.orders.config;

import com.VirtualCoffee.orders.repository.OrderRepository;
import com.VirtualCoffee.orders.service.BeverageClient;
import com.VirtualCoffee.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    private final OrderRepository orderRepository;
    private final BeverageClient beverageClient;

    @Bean
    public OrderService orderService() {
        return new OrderService(orderRepository, beverageClient);
    }

}
