package ecommercespringlabs.lab1.dto.order;

import ecommercespringlabs.lab1.common.OrderStatus;
import ecommercespringlabs.lab1.dto.customer.CustomerResponseDto;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class OrderResponseDto {
    UUID orderId;
    CustomerResponseDto customer;
    List<OrderEntryDto> orderItems;
    double totalPrice;
    OrderStatus orderStatus;
}