package com.VirtualCoffee.orders.controller;

import com.VirtualCoffee.orders.controller.request.OrderRequest;
import com.VirtualCoffee.orders.exception.BeverageUnavailableException;
import com.VirtualCoffee.orders.model.Order;
import com.VirtualCoffee.orders.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    void testCreateOrderEndpoint_ShouldReturn201() throws Exception {
        OrderRequest request = new OrderRequest("Latte", "M");
        Order response = new Order(1L, "Latte", "M");

        when(orderService.createOrder(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Latte\", \"size\":\"M\"}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Latte"));
    }

    @Test
    void testGetOrdersEndpoint_ShouldReturnList() throws Exception {
        List<Order> orders = List.of(new Order(1L, "Latte", "M"));
        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(orderService).getAllOrders();
    }

    @Test
    void testCreateOrderEndpoint_WithEmptyName_ShouldReturn400() throws Exception {
        when(orderService.createOrder(any())).thenThrow(new IllegalArgumentException("Name cannot be null or empty"));
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\", \"size\":\"M\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateOrderEndpoint_WithNullName_ShouldReturn400() throws Exception {
        when(orderService.createOrder(any())).thenThrow(new IllegalArgumentException("Name cannot be null or empty"));
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"size\":\"M\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateOrderEndpoint_WithBeverageUnavailable_ShouldReturn503() throws Exception {
        when(orderService.createOrder(any())).thenThrow(new BeverageUnavailableException());
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Latte\", \"size\":\"M\"}"))
                .andExpect(status().isServiceUnavailable());
    }
}
