package ecommercespringlabs.lab1.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.List;
@Value
public class OrderRequestDto {
    @NotNull(message = "Items cannot be null")
    List<OrderEntryDto> orderEntries;
    @NotNull(message = "Price cannot be null")
    @Min(value = 0)
    Double totalPrice;
    String customerId;
}