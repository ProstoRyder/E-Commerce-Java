package ecommercespringlabs.lab1.service;

import ecommercespringlabs.lab1.domain.order.Order;
import ecommercespringlabs.lab1.domain.order.OrderEntry;
import ecommercespringlabs.lab1.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {OrderServiceImpl.class})
public class OrderServiceTest {
    @MockBean
    private OrderService orderService;
    private Order order;
    @Mock
    private CustomerService customerService;

    @BeforeEach
    public void init() {
        OrderEntry orderEntry = OrderEntry.builder()
                .product("Mars")
                .count(2)
                .build();
        order = Order.builder()
                .id(UUID.randomUUID())
                .customer(customerService.findCustomerDetailsById(1L))
                .totalPrice(823)
                .entries(List.of(orderEntry))
                .build();
    }

    @Test
    void getOrders() {
        List<Order> mockOrder = List.of(order);
        Mockito.when(orderService.findAllOrder()).thenReturn(mockOrder);
        assertNotNull(orderService.findAllOrder());
    }

    @Test
    void findOrderById() {
        String id = order.getId().toString();
        Mockito.when(orderService.findOrderById(id)).thenReturn(order);
        assertNotNull(orderService.findOrderById(id));
    }

}
