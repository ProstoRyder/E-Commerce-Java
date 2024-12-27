package ecommercespringlabs.lab1.service;

import ecommercespringlabs.lab1.domain.Product;
import ecommercespringlabs.lab1.dto.product.ProductRequestDto;
import ecommercespringlabs.lab1.service.exception.ProductNotFoundException;
import ecommercespringlabs.lab1.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {ProductServiceImpl.class})
public class ProductServiceTest {

        @MockBean
        private ProductService productService;
        private Product product;
        @Mock
        private CategoryService categoryService;

        @BeforeEach
        public void init() {
            product = Product.builder().id(UUID.randomUUID()).title("test").description("test description").price(52.1).category(categoryService.findCategoryById("test id")).build();
        }

        @Test
        void getProducts() {
            List<Product> mockProducts = List.of(product);
            Mockito.when(productService.findAllProducts()).thenReturn(mockProducts);
            assertNotNull(productService.findAllProducts());
        }

        @Test
        void findProductById() {
            String id = product.getId().toString();
            Mockito.when(productService.findProductById(id)).thenReturn(product);
            assertNotNull(productService.findProductById(id));
        }

        @Test
        void addProduct_Success() {
            ProductRequestDto productRequestDto = new ProductRequestDto("New Product","This is a test product",99.99,"123e4567-e89b-12d3-a456-426614174000");
            Mockito.when(productService.addProduct(productRequestDto)).thenReturn(product);
            assertNotNull(productService.addProduct(productRequestDto));
        }


        @Test
        void deleteProduct_Success() {
            String id = product.getId().toString();
            productService.deleteProduct(id);
            Mockito.verify(productService, Mockito.times(1)).deleteProduct(id);
        }

        @Test
        void updateProduct_Success() {
            String id = product.getId().toString();
            ProductRequestDto updateRequest = new ProductRequestDto("Updated Product","This is a test product",99.99,"123e4567-e89b-12d3-a456-426614174000");
            Mockito.when(productService.updateProduct(updateRequest, id)).thenReturn(product);
            assertNotNull(productService.updateProduct(updateRequest, id));
        }

        @Test
        void updateProduct_Invalid() {
            String invalidId = "non-existent-id";
            ProductRequestDto updateRequest = new ProductRequestDto("Updated Product","This is a test product",99.99,"");
            Mockito.when(productService.updateProduct(updateRequest, invalidId))
                    .thenThrow(new ProductNotFoundException("Invalid product ID"));
            assertThrows(ProductNotFoundException.class,()->productService.updateProduct(updateRequest, invalidId));
        }

}
