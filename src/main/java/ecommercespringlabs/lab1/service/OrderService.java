package ecommercespringlabs.lab1.service;

import ecommercespringlabs.lab1.domain.order.Order;


import java.util.List;

public interface OrderService {
    Order findOrderById(String id);
    List<Order> findAllOrder();
}
