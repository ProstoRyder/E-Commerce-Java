package ecommercespringlabs.lab1.service.mapper;

import ecommercespringlabs.lab1.domain.Category;
import ecommercespringlabs.lab1.domain.Product;
import ecommercespringlabs.lab1.dto.product.ProductResponseDto;
import ecommercespringlabs.lab1.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class ProductMapperTest {
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @MockBean
    private Product product;
    private List<Product> productList;
    @Mock
    private CategoryService categoryService;
    private Category category;

    @BeforeEach
    void init() {
        product = Product.builder().id(UUID.randomUUID().toString()).title("test").description("test description").price(52.1).build();
        productList = List.of(product);
    }

    @Test
    void toProductResponseDto() {
        ProductResponseDto productResponseDto = productMapper.toProductProductDto(product);
        assertNotNull(productResponseDto);
        assertEquals(product.getId(), productResponseDto.getId());
        assertEquals(product.getTitle(), productResponseDto.getTitle());
    }

    @Test
    void toProductResponseDtoList() {
        List<ProductResponseDto> productResponseDtoList = productMapper.toProductResponseDtoList(productList);

        assertNotNull(productResponseDtoList);
        assertEquals(productList.size(), productResponseDtoList.size());
    }

}
