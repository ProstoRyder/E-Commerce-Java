package ecommercespringlabs.lab1.dto.order;

import ecommercespringlabs.lab1.common.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.List;
@Value
@Builder(toBuilder = true)
public class OrderRequestDto {
    @NotNull(message = "Items cannot be null")
    List<OrderEntryDto> orderEntries;
    String customerId;
    OrderStatus orderStatus;
}