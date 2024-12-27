package ecommercespringlabs.lab1.service.mapper;

import ecommercespringlabs.lab1.domain.Category;
import ecommercespringlabs.lab1.dto.category.CategoryResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto toCategoryResponseDto(Category category);
    List<CategoryResponseDto> toCategoryResponseDtoList(List<Category> categories);
}
