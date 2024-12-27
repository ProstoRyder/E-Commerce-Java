package ecommercespringlabs.lab1.dto.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponseDto {
    String id;
    String title;
}
