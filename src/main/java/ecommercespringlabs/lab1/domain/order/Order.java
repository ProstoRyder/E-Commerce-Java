package ecommercespringlabs.lab1.domain.order;

import ecommercespringlabs.lab1.common.OrderStatus;
import ecommercespringlabs.lab1.domain.Customer;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class Order {
    String id;
    List<OrderEntry> entries;
    Customer customer;
    int totalPrice;
    OrderStatus orderStatus;
}
