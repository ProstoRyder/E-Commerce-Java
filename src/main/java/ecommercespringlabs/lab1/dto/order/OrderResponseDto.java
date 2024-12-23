package ecommercespringlabs.lab1.dto.order;

import ecommercespringlabs.lab1.common.OrderStatus;
import ecommercespringlabs.lab1.dto.customer.CustomerResponseDto;
import lombok.Value;

@Value
public class OrderResponseDto {
    String orderId;
    CustomerResponseDto customer;
    double totalPrice;
    OrderStatus orderStatus;
}