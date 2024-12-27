package ecommercespringlabs.lab1.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecommercespringlabs.lab1.domain.Product;
import ecommercespringlabs.lab1.dto.product.ProductRequestDto;
import ecommercespringlabs.lab1.service.CategoryService;
import ecommercespringlabs.lab1.service.ProductService;
import ecommercespringlabs.lab1.service.exception.ProductNotFoundException;
import ecommercespringlabs.lab1.service.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private CategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    private Product product;

    private ProductRequestDto productRequestDto;

    @BeforeEach
    public void init() {
        product = Product.builder().id(UUID.randomUUID())
                .id(UUID.randomUUID())
                .title("test")
                .description("Galaxy Nova, System Orion, Zone 7")
                .price(52.1)
                .category(categoryService.findCategoryById("test id"))
                .build();

        productRequestDto = ProductRequestDto.builder()
                .title("test")
                .description("Galaxy Nova, System Orion, Zone 7")
                .price(52.1)
                .categoryId("test id")
                .build();
    }

    @Test
    public void shouldGetAllProducts() throws Exception {
        List<Product> response = List.of(product);
        Mockito.when(productService.findAllProducts()).thenReturn(response);

        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(productMapper.toProductResponseDtoList(response))
                ));
    }

    @Test
    public void shouldGetProductById() throws Exception {
        Mockito.when(productService.findProductById(anyString())).thenReturn(product);

        mockMvc.perform(get("/api/v1/products/{productId}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(productMapper.toProductProductDto(product))
                ));
    }

    @Test
    public void shouldAddProduct() throws Exception {
        Mockito.when(productService.addProduct(any())).thenReturn(product);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productMapper.toProductProductDto(product))));
    }

    @Test
    public void shouldDeleteProduct() throws Exception {
        String successMessage = "Product deleted successfully!";
        productService.deleteProduct(anyString());

        mockMvc.perform(delete("/api/v1/products/{productId}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        Mockito.when(productService.updateProduct(any(), anyString())).thenReturn(product);

        mockMvc.perform(put("/api/v1/products/{productId}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productMapper.toProductProductDto(product))));
    }

    @Test
    public void shouldGetNotFoundExceptionWhenProductDoesNotExist() throws Exception {
        Mockito.when(productService.findProductById(anyString())).thenThrow(new ProductNotFoundException("invalid-id"));
        mockMvc.perform(get("/api/v1/products/{productId}", "invalid-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
