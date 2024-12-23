package ecommercespringlabs.lab1.service;

import ecommercespringlabs.lab1.domain.order.Order;
import ecommercespringlabs.lab1.dto.order.OrderEntryRequestDto;
import ecommercespringlabs.lab1.dto.order.OrderRequestDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order findOrderById(UUID id);
    List<Order> findAllOrder();
    Order addOrder(OrderRequestDto orderRequestDto, OrderEntryRequestDto orderEntryRequestDto);
    void deleteOrder(UUID id);
}
