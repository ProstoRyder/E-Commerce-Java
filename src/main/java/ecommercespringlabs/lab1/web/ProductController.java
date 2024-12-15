package ecommercespringlabs.lab1.web;

import ecommercespringlabs.lab1.domain.Product;
import ecommercespringlabs.lab1.dto.product.ProductRequestDto;
import ecommercespringlabs.lab1.dto.product.ProductResponseDto;
import ecommercespringlabs.lab1.service.ProductService;
import ecommercespringlabs.lab1.service.mapper.ProductMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable String productId) {
        return ResponseEntity.ok(productMapper.toProductProductDto(productService.findProductById(productId)));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        Product product = productService.addProduct(productRequestDto);
        return ResponseEntity.ok(productMapper.toProductProductDto(product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody @Valid ProductRequestDto productRequestDto, @PathVariable String productId) {
        Product product = productService.updateProduct(productRequestDto, productId);
        return ResponseEntity.ok(productMapper.toProductProductDto(product));
    }
}
