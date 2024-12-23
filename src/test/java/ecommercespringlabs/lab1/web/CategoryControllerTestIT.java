package ecommercespringlabs.lab1.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecommercespringlabs.lab1.AbstractIt;
import ecommercespringlabs.lab1.dto.category.CategoryRequestDto;
import ecommercespringlabs.lab1.repository.CategoryRepository;
import ecommercespringlabs.lab1.repository.entity.CategoryEntity;
import ecommercespringlabs.lab1.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@AutoConfigureMockMvc
@SpringBootTest
public class CategoryControllerTestIT extends AbstractIt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private CategoryService categoryService;

    @BeforeEach
    void init() {
        Mockito.reset(categoryService);
        categoryRepository.deleteAll();
    }

    private CategoryRequestDto createCategoryDto() {
        return CategoryRequestDto.builder().title("Test").build();
    }
    private CategoryEntity saveCategoryEntity() {
        return categoryRepository.save(CategoryEntity.builder().title("Test").category_reference(UUID.fromString("123e4567-e89b-12d3-a456-426614174010")).build());
    }

    @Test
    void shouldAddCategory() throws Exception {
        CategoryRequestDto categoryRequestDto = createCategoryDto();

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldAddCategory_BadRequest() throws Exception {
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder().title("").build();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryRequestDto)));
        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldGetAllCategories() throws Exception {
        saveCategoryEntity();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldGetCategoryById() throws Exception {
        CategoryEntity savedCategory = saveCategoryEntity();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/{id}", savedCategory.getCategory_reference()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void shouldUpdateCategory() throws Exception {
        CategoryEntity savedCategory = saveCategoryEntity();
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                .title("Updated")
                .build();

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories/{id}", savedCategory.getCategory_reference())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateCategory_NotFound() throws Exception {
        UUID nonExistentCategoryId = UUID.randomUUID();
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                .title("Updated Category Title")
                .build();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories/{id}", nonExistentCategoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryRequestDto)));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldUpdateCategory_BadRequest() throws Exception {
        CategoryEntity savedCategory = saveCategoryEntity();
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                .title("")
                .build();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories/{id}", savedCategory.getCategory_reference())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryRequestDto)));
        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void shouldDeleteCategory() throws Exception {
        CategoryEntity savedCategory = saveCategoryEntity();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/categories/{id}", savedCategory.getCategory_reference()));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteCategory_NotFound() throws Exception {
        UUID nonExistentCategoryId = UUID.randomUUID();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/categories/{id}", nonExistentCategoryId));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldGetNotFoundExceptionWhenCategoryDoesNotExist() throws Exception {
        UUID nonExistentCategoryId = UUID.fromString("123e4567-e89b-12d3-a456-426614174011");
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/{id}", nonExistentCategoryId));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
