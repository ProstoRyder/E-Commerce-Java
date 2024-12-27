package ecommercespringlabs.lab1.domain.order;

import java.util.List;
import java.util.UUID;

import ecommercespringlabs.lab1.common.OrderStatus;
import ecommercespringlabs.lab1.domain.Customer;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Order {
    UUID id;
    List<OrderEntry> entries;
    Customer customer;
    int totalPrice;
    OrderStatus orderStatus;
}
