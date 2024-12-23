package ecommercespringlabs.lab1.service.mapper;

import ecommercespringlabs.lab1.domain.order.Order;
import ecommercespringlabs.lab1.domain.order.OrderEntry;
import ecommercespringlabs.lab1.dto.order.OrderEntryDto;
import ecommercespringlabs.lab1.dto.order.OrderResponseDto;
import ecommercespringlabs.lab1.repository.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    List<OrderEntry> toOrderEntries(List<Order> orders);
    List<OrderEntryDto> toOrderEntryDtos(List<Order> orders);
    @Mapping(target = "orderId", source = "id")
    OrderResponseDto toOrderResponseDto(Order order);
    List<OrderResponseDto> toOrderResponseDtoList(List<Order> order);
    @Mapping(target = "id", source = "orders_reference")
    Order toOrder(OrderEntity orderEntity);
    List<Order> toOrderList(Iterable<OrderEntity> orderEntities);
}
