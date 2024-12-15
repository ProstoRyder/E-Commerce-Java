package ecommercespringlabs.lab1.dto.product;

import ecommercespringlabs.lab1.domain.Category;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@Builder
@Jacksonized
public class ProductResponseDto {
    UUID id;
    String title;
    String description;
    Double price;
    Category category;
}
