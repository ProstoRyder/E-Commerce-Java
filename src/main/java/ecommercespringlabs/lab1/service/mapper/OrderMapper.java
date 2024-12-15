package ecommercespringlabs.lab1.service.mapper;

import ecommercespringlabs.lab1.domain.order.Order;
import ecommercespringlabs.lab1.dto.order.OrderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "orderItems", source = "entries")
    OrderResponseDto toOrderResponseDto(Order order);
    List<OrderResponseDto> toOrderResponseDtoList(List<Order> order);
}
