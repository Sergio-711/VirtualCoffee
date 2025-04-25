package com.VirtualCoffee.orders.repository;

import com.VirtualCoffee.orders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
