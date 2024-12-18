package ecommercespringlabs.lab1.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecommercespringlabs.lab1.domain.order.Order;
import ecommercespringlabs.lab1.domain.order.OrderEntry;
import ecommercespringlabs.lab1.service.CustomerService;
import ecommercespringlabs.lab1.service.OrderService;
import ecommercespringlabs.lab1.service.exception.OrderNotFoundException;
import ecommercespringlabs.lab1.service.mapper.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class OrderControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Mock
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private Order order;

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
    public void shouldGetAllOrders() throws Exception {
        List<Order> response = List.of(order);
        Mockito.when(orderService.findAllOrder()).thenReturn(response);

        mockMvc.perform(get("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(orderMapper.toOrderResponseDtoList(response))
                ));
    }

    @Test
    public void shouldGetOrderById() throws Exception {
        Mockito.when(orderService.findOrderById(anyString())).thenReturn(order);

        mockMvc.perform(get("/api/v1/orders/{id}", order.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(orderMapper.toOrderResponseDto(order))
                ));
    }

    @Test
    public void shouldGetNotFoundExceptionWhenOrderDoesNotExist() throws Exception {
        Mockito.when(orderService.findOrderById(anyString())).thenThrow(new OrderNotFoundException("invalid-id"));
        mockMvc.perform(get("/api/v1/orders/{orderId}", "invalid-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
