package ecommercespringlabs.lab1.service.mapper;

import ecommercespringlabs.lab1.domain.Category;
import ecommercespringlabs.lab1.dto.category.CategoryResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class CategoryMapperTest {
    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    private Category category;
    private List<Category> categoryList;

    @BeforeEach
    void init() {
        category = Category.builder().id(UUID.randomUUID().toString()).title("test").build();
        categoryList = List.of(category);
    }

    @Test
    void toCategoryResponseDto() {
        CategoryResponseDto categoryResponseDto = categoryMapper.toCategoryResponseDto(category);
        assertNotNull(categoryResponseDto);
        assertEquals(category.getId().toString(), categoryResponseDto.getId());
        assertEquals(category.getTitle(), categoryResponseDto.getTitle());
    }

    @Test
    void toCategoryResponseDtoList() {
        List<CategoryResponseDto> categoryResponseDtoList = categoryMapper.toCategoryResponseDtoList(categoryList);

        assertNotNull(categoryResponseDtoList);
        assertEquals(categoryList.size(), categoryResponseDtoList.size());
    }
}
