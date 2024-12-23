package ecommercespringlabs.lab1.dto.product;

import ecommercespringlabs.lab1.domain.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDto {
    String id;
    String title;
    String description;
    Double price;
    Category category;
}