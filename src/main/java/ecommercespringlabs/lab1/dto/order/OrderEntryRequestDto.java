package ecommercespringlabs.lab1.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class OrderEntryRequestDto {
    @NotNull(message = "Name cannot be null")
    String product;

    @NotNull(message = "Quantity cannot be null")
    Integer count;

    @NotNull(message = "Price cannot be null")
    Double price;
}
