package ecommercespringlabs.lab1.service;

import ecommercespringlabs.lab1.domain.Product;
import ecommercespringlabs.lab1.dto.product.ProductRequestDto;
import ecommercespringlabs.lab1.repository.projection.ProductDetailsProjection;


import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> findAllProducts();
    Product addProduct(ProductRequestDto product);
    Product updateProduct(ProductRequestDto product, UUID id);
    void deleteProduct(UUID productId);
    Product findProductById(UUID id);
    List<ProductDetailsProjection> findAllByCategoryId(UUID categoryId);
}
