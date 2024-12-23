package ecommercespringlabs.lab1.service.impl;

import ecommercespringlabs.lab1.domain.Product;
import ecommercespringlabs.lab1.dto.product.ProductRequestDto;
import ecommercespringlabs.lab1.repository.CategoryRepository;
import ecommercespringlabs.lab1.repository.ProductRepository;
import ecommercespringlabs.lab1.repository.entity.CategoryEntity;
import ecommercespringlabs.lab1.repository.entity.ProductEntity;
import ecommercespringlabs.lab1.repository.projection.ProductDetailsProjection;
import ecommercespringlabs.lab1.service.ProductService;
import ecommercespringlabs.lab1.service.exception.CategoryNotFoundException;
import ecommercespringlabs.lab1.service.exception.ProductNotFoundException;
import ecommercespringlabs.lab1.service.mapper.ProductMapper;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAllProducts() {
        return productMapper.toProductList(productRepository.findAll());
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProductDetailsProjection> findAllByCategoryId (UUID categoryId) {
        return productRepository.findAllByCategoryId(categoryId);
    }

    @Override
    @Transactional
    public Product findProductById(UUID id) {
        ProductEntity product = productRepository.findByNaturalId(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toProduct(product);
    }

    @Override
    @Transactional
    public Product addProduct(ProductRequestDto productRequestDto) {
        CategoryEntity category = categoryRepository.findByNaturalId(UUID.fromString(productRequestDto.getCategoryId())).orElseThrow(() -> new CategoryNotFoundException(productRequestDto.getCategoryId()));
        ProductEntity newProduct = ProductEntity.builder().title(productRequestDto
                .getTitle())
                .product_reference(UUID.randomUUID())
                .title(productRequestDto.getTitle())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .category(category)
                .build();
        try {
            return productMapper.toProduct(productRepository.save(newProduct));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }



    @Override
    public Product updateProduct(ProductRequestDto productRequestDto, UUID id) {
        ProductEntity product = productRepository.findByNaturalId(id).orElseThrow(() -> new ProductNotFoundException(id));
        CategoryEntity category = categoryRepository.findByNaturalId(UUID.fromString(productRequestDto.getCategoryId())).orElseThrow(() -> new CategoryNotFoundException(productRequestDto.getCategoryId()));
        product.setProduct_reference(id);

        product.setTitle(productRequestDto.getTitle());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setCategory(category);

        try {
            return productMapper.toProduct(productRepository.save(product));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        findProductById(id);
        try {
            productRepository.deleteByNaturalId(id);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

}
