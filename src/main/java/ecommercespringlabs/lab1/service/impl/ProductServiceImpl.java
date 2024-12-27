package ecommercespringlabs.lab1.service.impl;

import ecommercespringlabs.lab1.domain.Product;
import ecommercespringlabs.lab1.dto.product.ProductRequestDto;
import ecommercespringlabs.lab1.service.CategoryService;
import ecommercespringlabs.lab1.service.ProductService;
import ecommercespringlabs.lab1.service.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class ProductServiceImpl implements ProductService {

    List<Product> products = new ArrayList<>();

    private final CategoryService categoryService;

    public ProductServiceImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
        products.add(Product.builder()
                .id(UUID.randomUUID())
                .title("Mars Model")
                .description("A detailed model of the planet Mars.")
                .price(34.99)
                .category(categoryService.findCategoryById("123e4567-e89b-12d3-a456-426614174000"))
                .build());
        products.add(Product.builder()
                .id(UUID.randomUUID())
                .title("Moon Poster")
                .description("High-resolution poster of Earth's Moon.")
                .price(14.99)
                .category(categoryService.findCategoryById("123e4512-e89b-12d3-a456-426685390400"))
                .build());
    }

    @Override
    public List<Product> findAllProducts() {
        return products;
    }

    @Override
    public Product findProductById(String id) {
        return products.stream().filter(product -> product.getId().equals(UUID.fromString(id)))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product addProduct(ProductRequestDto productRequestDto) {
        Product product = Product.builder()
                .id(UUID.randomUUID())
                .title(productRequestDto.getTitle())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .category(categoryService.findCategoryById(productRequestDto.getCategoryId()))
                .build();
        products.add(product);
        return product;
    }


    @Override
    public Product updateProduct(ProductRequestDto productRequestDto, String id) {
        Product product = findProductById(id);
        product.setTitle(productRequestDto.getTitle());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setCategory(categoryService.findCategoryById(productRequestDto.getCategoryId()));
        return product;
    }

    @Override
    public void deleteProduct(String id) {
        Product product = findProductById(id);
        products.remove(product);
    }

}
