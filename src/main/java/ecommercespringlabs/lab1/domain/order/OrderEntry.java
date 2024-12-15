package ecommercespringlabs.lab1.domain.order;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class OrderEntry {
    String product;
    int count;
}
