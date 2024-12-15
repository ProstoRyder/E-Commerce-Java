package ecommercespringlabs.lab1.service.mapper;
import ecommercespringlabs.lab1.domain.Product;
import ecommercespringlabs.lab1.dto.product.ProductResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDto toProductProductDto(Product product);
    List<ProductResponseDto> toProductResponseDtoList(List<Product> product);
}
