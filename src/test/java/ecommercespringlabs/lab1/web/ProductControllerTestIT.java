package ecommercespringlabs.lab1.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecommercespringlabs.lab1.AbstractIt;
import ecommercespringlabs.lab1.dto.product.ProductRequestDto;
import ecommercespringlabs.lab1.repository.CategoryRepository;
import ecommercespringlabs.lab1.repository.ProductRepository;
import ecommercespringlabs.lab1.repository.entity.CategoryEntity;
import ecommercespringlabs.lab1.repository.entity.ProductEntity;
import ecommercespringlabs.lab1.service.ProductService;
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
public class ProductControllerTestIT extends AbstractIt {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private ProductService productService;


    @BeforeEach
    void init() {
        Mockito.reset(productService);
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }



    private ProductRequestDto createProductDto() {
        return ProductRequestDto.builder().title("Test")
                .description("Galaxy MilkyWay, System Alpha, Zone 123")
                .price(321.2)
                .categoryId("123e4567-e89b-12d3-a456-426614174060")
                .build();
    }
    private ProductEntity saveProductEntity() {
        CategoryEntity categoryEntity = createCategory();
        return productRepository.save(ProductEntity.builder().title("Test")
                .description("Galaxy MilkyWay, System Alpha, Zone 123")
                .price(321.2)
                .category(categoryEntity)
                .product_reference(UUID.fromString("123e4567-e89b-12d3-a456-426614174061")).build());
    }
    private CategoryEntity createCategory() {
        return categoryRepository.save(CategoryEntity.builder()
                .title("Test").category_reference(UUID.fromString("123e4567-e89b-12d3-a456-426614174060")).build());
    }



    @Test
    void shouldAddProduct() throws Exception {
        ProductRequestDto productRequestDto = createProductDto();
        createCategory();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(productRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldAddProduct_BadRequest() throws Exception {
        createCategory();
        ProductRequestDto productRequestDto = ProductRequestDto.builder().title("")
                .description("Galaxy MilkyWay, System Alpha, Zone 123")
                .price(321.2)
                .categoryId("123e4567-e89b-12d3-a456-426614174060").build();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(productRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldGetAllProducts() throws Exception {
        saveProductEntity();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products")).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void shouldGetProductById() throws Exception {
        ProductEntity savedProduct = saveProductEntity();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{id}", savedProduct.getProduct_reference()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void shouldUpdateProduct() throws Exception {
        ProductEntity savedProduct = saveProductEntity();
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .title("Test")
                .description("Galaxy MilkyWay, System Alpha, Zone 123")
                .price(321.2)
                .categoryId("123e4567-e89b-12d3-a456-426614174060")
                .build();

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/{id}", savedProduct.getProduct_reference())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(productRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateProduct_NotFound() throws Exception {
        UUID nonExistentProductId = UUID.randomUUID();
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .title("Test")
                .description("Galaxy MilkyWay, System Alpha, Zone 123")
                .price(321.2)
                .categoryId("123e4567-e89b-12d3-a456-426614174060")
                .build();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/{id}", nonExistentProductId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(productRequestDto)));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldUpdateProduct_BadRequest() throws Exception {
        ProductEntity savedProduct = saveProductEntity();
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .title("")
                .description("Galaxy MilkyWay, System Alpha, Zone 123")
                .price(321.2)
                .categoryId("123e4567-e89b-12d3-a456-426614174060")
                .build();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/{id}", savedProduct.getProduct_reference())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(productRequestDto)));
        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void shouldDeleteProduct() throws Exception {
        ProductEntity savedProduct = saveProductEntity();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/{id}", savedProduct.getProduct_reference()));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteProduct_NotFound() throws Exception {
        UUID nonExistentProductId = UUID.randomUUID();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/{id}", nonExistentProductId));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldGetNotFoundExceptionWhenProductDoesNotExist() throws Exception {
        UUID nonExistentProductId = UUID.fromString("123e4567-e89b-12d3-a456-426614174062");
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{id}", nonExistentProductId));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldGetProductsByCategoryId() throws Exception {
        UUID productCategoryId = UUID.randomUUID();
        CategoryEntity savedCategory = CategoryEntity.builder()
                .title("Test").category_reference(productCategoryId).build();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/by-category/{categoryId}", savedCategory.getCategory_reference())
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
