package ecommercespringlabs.lab1.service;

import ecommercespringlabs.lab1.domain.Category;
import ecommercespringlabs.lab1.dto.category.CategoryRequestDto;
import ecommercespringlabs.lab1.service.exception.CategoryNotFoundException;
import ecommercespringlabs.lab1.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes = CategoryServiceImpl.class)
class CategoryServiceTest {

    @MockBean
    private CategoryService categoryService;
    private Category category;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl();
    }

    @Test
    void findAllCategories_Success() {
        List<Category> categories = categoryService.findAllCategories();
        assertNotNull(categories);
    }

    @Test
    void findCategoryById_Success() {
        String validId = "123e4567-e89b-12d3-a456-426614174000";
        Category category = categoryService.findCategoryById(validId);
        assertNotNull(category);
        assertEquals(validId, category.getId().toString());
    }

    @Test
    void findCategoryById_Invalid() {
        String invalidId = "00000000-0000-0000-0000-000000000000";
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.findCategoryById(invalidId);
        });
    }

    @Test
    void addCategory_Success() {
        CategoryRequestDto request = new CategoryRequestDto("New Space Exploration");
        Category category = categoryService.addCategory(request);
        assertNotNull(category);
        assertEquals(request.getTitle(), category.getTitle());
    }

    @Test
    void deleteCategory_Invalid() {
        String invalidId = "00000000-0000-0000-0000-000000000000";
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.deleteCategory(invalidId);
        });
    }

    @Test
    void updateCategory_Success() {
        String validId = "123e4567-e89b-12d3-a456-426614174000"; // Існуючий ID
        CategoryRequestDto updateRequest = new CategoryRequestDto("Updated Galaxies");
        Category result = categoryService.updateCategory(updateRequest, validId);
        assertNotNull(result);
        assertEquals(updateRequest.getTitle(), result.getTitle());
        assertEquals(validId, result.getId().toString());
    }

    @Test
    void updateCategory_Invalid() {
        String invalidId = "00000000-0000-0000-0000-000000000000";
        CategoryRequestDto updateRequest = new CategoryRequestDto("Attempted Update");
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.updateCategory(updateRequest, invalidId);
        });
    }
}

