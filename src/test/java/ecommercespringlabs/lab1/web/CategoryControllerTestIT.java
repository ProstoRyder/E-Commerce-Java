package ecommercespringlabs.lab1.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecommercespringlabs.lab1.domain.Category;
import ecommercespringlabs.lab1.dto.category.CategoryRequestDto;
import ecommercespringlabs.lab1.service.CategoryService;
import ecommercespringlabs.lab1.service.exception.CategoryNotFoundException;
import ecommercespringlabs.lab1.service.mapper.CategoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class CategoryControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    private Category category;

    private CategoryRequestDto categoryRequestDto;

    @BeforeEach
    public void init() {
        category = Category.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).title("test").build();
        categoryRequestDto = CategoryRequestDto.builder().title("test").build();
    }

    @Test
    public void shouldGetAllCategories() throws Exception {
        List<Category> response = List.of(category);
        Mockito.when(categoryService.findAllCategories()).thenReturn(response);

        mockMvc.perform(get("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryMapper.toCategoryResponseDtoList(response))));
    }

    @Test
    public void shouldGetCategoryById() throws Exception {
        Mockito.when(categoryService.findCategoryById("123e4567-e89b-12d3-a456-426614174000")).thenReturn(category);

        mockMvc.perform(get("/api/v1/categories/{categoryId}", category.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryMapper.toCategoryResponseDto(category))));
    }

    @Test
    public void shouldAddCategory() throws Exception {
        Mockito.when(categoryService.addCategory(categoryRequestDto)).thenReturn(category);

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(categoryRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryMapper.toCategoryResponseDto(category))));
    }

    @Test
    public void shouldAddCategory_BadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateCategory() throws Exception {
        Mockito.when(categoryService.updateCategory(categoryRequestDto, "123e4567-e89b-12d3-a456-426614174000")).thenReturn(category);

        mockMvc.perform(put("/api/v1/categories/{categoryId}", category.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryMapper.toCategoryResponseDto(category))));
    }

    @Test
    public void shouldUpdateCategory_NotFound() throws Exception {
        Mockito.when(categoryService.updateCategory(categoryRequestDto, "123e4567-e89b-12d3-a456-426614174000")).thenThrow(CategoryNotFoundException.class);

        mockMvc.perform(put("/api/v1/categories/{categoryId}", category.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateCategory_BadRequest() throws Exception {
        mockMvc.perform(put("/api/v1/categories/{categoryId}", category.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldDeleteCategory() throws Exception {
        String successMessage = "Category deleted successfully!";
        categoryService.deleteCategory("123e4567-e89b-12d3-a456-426614174000");

        mockMvc.perform(delete("/api/v1/categories/{categoryId}", category.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteCategory_NotFound() throws Exception {
        String categoryId = "123e4567-e89b-12d3-a456-426614174000";
        Mockito.doThrow(new CategoryNotFoundException("Category with ID " + categoryId + " Not found"))
                .when(categoryService).deleteCategory(categoryId);
        mockMvc.perform(delete("/api/v1/categories/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetNotFoundExceptionWhenCategoryDoesNotExist() throws Exception {
        Mockito.when(categoryService.findCategoryById(anyString())).thenThrow(new CategoryNotFoundException("invalid-id"));
                mockMvc.perform(get("/api/v1/categories/{categoryId}", "invalid-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
