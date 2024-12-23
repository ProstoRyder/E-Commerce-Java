package ecommercespringlabs.lab1.web;

import ecommercespringlabs.lab1.domain.Product;
import ecommercespringlabs.lab1.dto.product.ProductRequestDto;
import ecommercespringlabs.lab1.dto.product.ProductResponseDto;
import ecommercespringlabs.lab1.repository.projection.ProductDetailsProjection;
import ecommercespringlabs.lab1.service.ProductService;
import ecommercespringlabs.lab1.service.mapper.ProductMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
@Validated
public class ProductController {
    private final ProductMapper productMapper;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productMapper.toProductResponseDtoList(productService.findAllProducts()));
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProductDetailsProjection>> findAllByCategoryId(@PathVariable UUID categoryId) {
        List<ProductDetailsProjection> products = productService.findAllByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID productId) {
        return ResponseEntity.ok(productMapper.toProductProductDto(productService.findProductById(productId)));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(productMapper.toProductProductDto(productService.addProduct(productRequestDto)));
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody @Valid ProductRequestDto productRequestDto, @PathVariable UUID productId) {
        Product product = productService.updateProduct(productRequestDto, productId);
        return ResponseEntity.ok(productMapper.toProductProductDto(product));
    }

}
