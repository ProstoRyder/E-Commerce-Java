package ecommercespringlabs.lab1.service;

import ecommercespringlabs.lab1.domain.Product;
import ecommercespringlabs.lab1.dto.product.ProductRequestDto;


import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();
    Product addProduct(ProductRequestDto product);
    Product updateProduct(ProductRequestDto product, String id);
    String deleteProduct(String productId);
    Product findProductById(String id);
}
