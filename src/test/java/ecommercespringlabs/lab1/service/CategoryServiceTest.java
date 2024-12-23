package ecommercespringlabs.lab1.service;

import ecommercespringlabs.lab1.domain.Category;
import ecommercespringlabs.lab1.dto.category.CategoryRequestDto;
import ecommercespringlabs.lab1.dto.category.CategoryResponseDto;
import ecommercespringlabs.lab1.repository.CategoryRepository;
import ecommercespringlabs.lab1.repository.entity.CategoryEntity;
import ecommercespringlabs.lab1.service.impl.CategoryServiceImpl;
import ecommercespringlabs.lab1.service.mapper.CategoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = CategoryServiceImpl.class)
class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    private UUID id;
    private CategoryRequestDto categoryRequestDto;
    private CategoryResponseDto categoryResponseDto;
    private CategoryEntity categoryEntity;
    private Category category;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        categoryRequestDto = CategoryRequestDto.builder()
                .title("Category 1")
                .build();

        categoryEntity = CategoryEntity.builder()
                .title("Category 1")
                .build();

        category = Category.builder()
                .id(id.toString())
                .title("Category 1")
                .build();
    }

    @Test
    void findAllCategories_Success() {
        List<CategoryEntity> categoryEntities = new ArrayList<>(List.of(categoryEntity));
        List<Category> categories = new ArrayList<>(List.of(category));

        when(categoryRepository.findAll()).thenReturn(categoryEntities);
        when(categoryMapper.toCategoryList(categoryEntities)).thenReturn(categories);

        List<Category> result = categoryService.findAllCategories();

        assertNotNull(result);
    }

    @Test
    void findCategoryById_Success() {
        when(categoryRepository.findByNaturalId(id)).thenReturn(Optional.of(categoryEntity));
        when(categoryMapper.toCategory(categoryEntity)).thenReturn(category);

        Category result = categoryService.findCategoryById(id);

        assertNotNull(result);
    }

    @Test
    void addCategory_Success() {
        CategoryEntity newCategory = CategoryEntity.builder()
                .title(categoryRequestDto.getTitle())
                .category_reference(UUID.randomUUID())
                .build();

        when(categoryRepository.save(Mockito.any(CategoryEntity.class))).thenReturn(newCategory);
        when(categoryMapper.toCategory(Mockito.any(CategoryEntity.class))).thenReturn(category);

        Category result = categoryService.addCategory(categoryRequestDto);

        assertNotNull(result);
    }

    @Test
    void deleteCategory_Success() {
        UUID categoryId = UUID.randomUUID();
        when(categoryRepository.findByNaturalId(categoryId)).thenReturn(Optional.of(categoryEntity));
        Mockito.doNothing().when(categoryRepository).deleteByNaturalId(categoryId);
        categoryService.deleteCategory(categoryId);
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteByNaturalId(categoryId);
    }


    @Test
    void updateCategory_Success() {
        when(categoryRepository.findByNaturalId(id)).thenReturn(Optional.of(categoryEntity));
        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(categoryMapper.toCategory(categoryEntity)).thenReturn(category);


        Category result = categoryService.updateCategory(categoryRequestDto, id);
    }

}

