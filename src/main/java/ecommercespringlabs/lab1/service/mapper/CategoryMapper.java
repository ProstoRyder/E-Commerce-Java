package ecommercespringlabs.lab1.service.mapper;

import ecommercespringlabs.lab1.domain.Category;
import ecommercespringlabs.lab1.dto.category.CategoryRequestDto;
import ecommercespringlabs.lab1.dto.category.CategoryResponseDto;
import ecommercespringlabs.lab1.repository.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto toCategoryResponseDto(Category category);
    List<CategoryResponseDto> toCategoryResponseDtoList(List<Category> categories);
    @Mapping(source = "category_reference", target = "id")
    Category toCategory(CategoryEntity categoryEntity);
    List<Category> toCategoryList(Iterable<CategoryEntity> categoryEntities);
    CategoryEntity toCategoryEntity(CategoryRequestDto categoryRequestDto);
}
