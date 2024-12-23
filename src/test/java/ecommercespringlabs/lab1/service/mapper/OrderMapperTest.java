package ecommercespringlabs.lab1.service.mapper;

import ecommercespringlabs.lab1.domain.Customer;
import ecommercespringlabs.lab1.domain.order.Order;
import ecommercespringlabs.lab1.domain.order.OrderEntry;
import ecommercespringlabs.lab1.dto.order.OrderResponseDto;
import ecommercespringlabs.lab1.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static ecommercespringlabs.lab1.common.OrderStatus.COMPLETED;
import static org.junit.Assert.*;

@SpringBootTest
public class OrderMapperTest {

    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    private Order order;
    private List<Order> orderList;
    @Mock
    private CustomerService customerService;
    private Customer customer;

    @BeforeEach
    void init() {
        OrderEntry orderItem = OrderEntry.builder()
                .product("Test")
                .count(2)
                .build();


        order = Order.builder()
                .id(UUID.randomUUID().toString())
                .entries(List.of(orderItem))
                .totalPrice(200)
                .orderStatus(COMPLETED)
                .build();


        orderList = List.of(order);
    }

    @Test
    void toOrderResponseDto() {
        OrderResponseDto orderResponseDto = orderMapper.toOrderResponseDto(order);

        assertNotNull(orderResponseDto);
        assertEquals(order.getId(), orderResponseDto.getOrderId());
        assertEquals(order.getOrderStatus(), orderResponseDto.getOrderStatus());
        assertEquals(order.getCustomer(), orderResponseDto.getCustomer());
    }

    @Test
    void toOrderResponseDtoList() {
        List<OrderResponseDto> orderResponseDtoList = orderMapper.toOrderResponseDtoList(orderList);

        assertNotNull(orderResponseDtoList);
        assertEquals(orderList.size(), orderResponseDtoList.size());

    }
}
