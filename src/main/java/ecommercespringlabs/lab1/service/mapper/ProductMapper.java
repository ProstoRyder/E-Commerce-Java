package ecommercespringlabs.lab1.service.mapper;

import ecommercespringlabs.lab1.domain.Product;
import ecommercespringlabs.lab1.dto.product.ProductResponseDto;
import ecommercespringlabs.lab1.repository.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDto toProductProductDto(Product product);
    List<ProductResponseDto> toProductResponseDtoList(List<Product> product);
    @Mapping(source = "product_reference", target = "id")
    Product toProduct(ProductEntity productEntity);
    List<Product> toProductList(Iterable<ProductEntity> productEntity);
}
