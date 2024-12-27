package ecommercespringlabs.lab1.dto.product;

import ecommercespringlabs.lab1.domain.Category;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProductResponseDto {
    UUID id;
    String title;
    String description;
    Double price;
    Category category;
}