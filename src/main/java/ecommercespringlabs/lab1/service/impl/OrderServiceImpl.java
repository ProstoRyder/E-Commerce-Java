package ecommercespringlabs.lab1.service.impl;

import ecommercespringlabs.lab1.common.OrderStatus;
import ecommercespringlabs.lab1.domain.order.Order;
import ecommercespringlabs.lab1.domain.order.OrderEntry;
import ecommercespringlabs.lab1.service.CustomerService;
import ecommercespringlabs.lab1.service.OrderService;
import ecommercespringlabs.lab1.service.exception.OrderNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final CustomerService customerService;

    List<Order> orders = new ArrayList<>();

    OrderEntry orderEntry1 = OrderEntry.builder()
            .product("Mars Model")
            .count(4)
            .build();

    public OrderServiceImpl(CustomerService customerService) {
        this.customerService = customerService;
        orders.add(Order.builder()
                .id(UUID.randomUUID())
                .customer(customerService.findCustomerDetailsById("123e4567-e89b-12d3-a456-426614174000"))
                .totalPrice(823)
                .entries(List.of(orderEntry1))
                .build());
        orders.add(Order.builder()
                .id(UUID.randomUUID())
                .customer(customerService.findCustomerDetailsById("123e4567-e89b-12d3-a456-426614174000"))
                .totalPrice(133)
                .entries(List.of(orderEntry1))
                .orderStatus(OrderStatus.COMPLETED)
                .build());
    }

    @Override
    public List<Order> findAllOrder() {
        return orders;
    }

    @Override
    public Order findOrderById(String id) {
        return orders.stream().filter(order -> order.getId().equals(UUID.fromString(id)))
                .findFirst()
                .orElseThrow(() -> new OrderNotFoundException(id));
    }


}
