package ecommercespringlabs.lab1.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
@Builder
@Value
public class OrderEntryDto {
    @NotNull(message = "Name cannot be null")
    String product;
    @NotNull(message = "Quantity cannot be null")
    int count;

}
