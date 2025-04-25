package com.VirtualCoffee.orders.service;

import com.VirtualCoffee.orders.controller.request.OrderRequest;
import com.VirtualCoffee.orders.exception.BeverageUnavailableException;
import com.VirtualCoffee.orders.model.Order;
import com.VirtualCoffee.orders.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private BeverageClient beverageClient;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testCreateOrder_WhenBeverageAvailable_ShouldCreateOrder() {
        // Arrange
        OrderRequest request = new OrderRequest("Latte", "M");
        when(beverageClient.isBeverageAvailable("Latte", "M")).thenReturn(true);

        Order savedOrder = new Order(1L, "Latte", "M");
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // Act
        Order result = orderService.createOrder(request);

        // Assert
        assertNotNull(result);
        assertEquals("Latte", result.getName());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void testCreateOrder_WhenBeverageNotAvailable_ShouldThrowException() {
        OrderRequest request = new OrderRequest("Mocha", "L");

        when(beverageClient.isBeverageAvailable("Mocha", "L")).thenReturn(false);

        assertThrows(BeverageUnavailableException.class, () -> {
            orderService.createOrder(request);
        });

        verify(orderRepository, never()).save(any());
    }

    @Test
    void testCreateOrder_InvalidName_ShouldThrowException() {
        OrderRequest request = new OrderRequest("", "M");

        assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(request);
        });
    }

    @Test
    void testGetAllOrders_ShouldReturnList() {
        List<Order> orders = Arrays.asList(
                new Order(1L, "Latte", "M"),
                new Order(2L, "Espresso", "S")
        );

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(2, result.size());
        assertEquals("Latte", result.get(0).getName());
    }
}
