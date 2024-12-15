package ecommercespringlabs.lab1.domain;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class Product {
    UUID id;
    String title;
    String description;
    Double price;
    Category category;
}
