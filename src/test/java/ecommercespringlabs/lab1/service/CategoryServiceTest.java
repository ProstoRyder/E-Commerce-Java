package ecommercespringlabs.lab1.service;

import ecommercespringlabs.lab1.domain.Category;
import ecommercespringlabs.lab1.dto.category.CategoryRequestDto;
import ecommercespringlabs.lab1.service.exception.CategoryNotFoundException;
import ecommercespringlabs.lab1.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes = CategoryServiceImpl.class)
class CategoryServiceTest {

    private CategoryService categoryService;

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
    void deleteCategory_Success() {
        String validId = "123e4567-e89b-12d3-a456-426614174000"; // Існуючий ID
        String result = categoryService.deleteCategory(validId);
        assertNotNull(result);
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


//@SpringBootTest(classes = CategoryServiceImpl.class)
//class CategoryServiceTest {
//
//    @MockBean
//    private CategoryService categoryService;
//
//    private Category category;
//
//
//    @BeforeEach
//    public void init() {
//        category = Category.builder().id(UUID.randomUUID()).title("test").build();
//    }
//
//    @Test
//    void getCategories() {
//        List<Category> mockCategories = List.of(category);
//        Mockito.when(categoryService.findAllCategories()).thenReturn(mockCategories);
//        assertNotNull(categoryService.findAllCategories());
//    }
//    @Test
//    void findCategoryById() {
//        String id = category.getId().toString();
//        Mockito.when(categoryService.findCategoryById(id)).thenReturn(category);
//        assertNotNull(categoryService.findCategoryById(id));
//    }
//
////    @Test
////    void findCategoryById_Invalid() {
////        Mockito.when(categoryService.findCategoryById("test"))
////                .thenThrow(new CategoryNotFoundException("Invalid category data"));
////        assertThrows(CategoryNotFoundException.class,()->categoryService.findCategoryById("Invalid category data"));
////    }
//
//    @Test
//    void addCategory_Success() {
//        CategoryRequestDto categoryRequestDto = new CategoryRequestDto("New Category");
//        Mockito.when(categoryService.addCategory(categoryRequestDto)).thenReturn(category);
//        assertNotNull(categoryService.addCategory(categoryRequestDto));
//    }
//
//
//    @Test
//    void deleteCategory_Success() {
//        String id = category.getId().toString();
//        Mockito.when(categoryService.deleteCategory(id)).thenReturn("Category was deleted");
//        assertNotNull(categoryService.deleteCategory(id));
//    }
//
//    @Test
//    void deleteCategory_Invalid() {
//        String invalidId = "123e4567-e89b-12d3-a456-426614174100";
//        Mockito.when(categoryService.deleteCategory(invalidId))
//                .thenThrow(new CategoryNotFoundException("Invalid category ID"));
//        assertThrows(CategoryNotFoundException.class,()->categoryService.deleteCategory(invalidId));
//    }
//
//    @Test
//    void updateCategory_Success() {
//        String id = category.getId().toString();
//        CategoryRequestDto updateRequest = new CategoryRequestDto("Updated Category");
//        Mockito.when(categoryService.updateCategory(updateRequest, id)).thenReturn(category);
//        assertNotNull(categoryService.updateCategory(updateRequest, id));
//    }
//
//    @Test
//    void updateCategory_Invalid() {
//        String invalidId = "123e4567-e89b-12d3-a456-426614174430";
//        CategoryRequestDto updateRequest = new CategoryRequestDto("Updated Category");
//        Mockito.when(categoryService.updateCategory(updateRequest, invalidId))
//                .thenThrow(new CategoryNotFoundException("Invalid category ID"));
//        assertThrows(CategoryNotFoundException.class,()->categoryService.updateCategory(updateRequest, invalidId));
//    }
//
//}
