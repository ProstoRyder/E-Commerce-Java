package ecommercespringlabs.lab1.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecommercespringlabs.lab1.AbstractIt;
import ecommercespringlabs.lab1.common.OrderStatus;
import ecommercespringlabs.lab1.dto.order.OrderEntryDto;
import ecommercespringlabs.lab1.dto.order.OrderRequestDto;
import ecommercespringlabs.lab1.repository.CategoryRepository;
import ecommercespringlabs.lab1.repository.CustomerRepository;
import ecommercespringlabs.lab1.repository.OrderRepository;
import ecommercespringlabs.lab1.repository.ProductRepository;
import ecommercespringlabs.lab1.repository.entity.*;
import ecommercespringlabs.lab1.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

@AutoConfigureMockMvc
@SpringBootTest
public class OrderControllerTestIT extends AbstractIt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private OrderService orderService;

    private ProductEntity category;

    @BeforeEach
    void init() {
        Mockito.reset(orderService);
        orderRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        customerRepository.deleteAll();
    }


    private OrderRequestDto createOrderEntity() {
        saveCustomerEntity();
        saveProductEntity();
        OrderEntryDto orderEntry = OrderEntryDto.builder()
                .count(2)
                .product("123e4567-e89b-12d3-a456-426614174031")
                .build();
        List<OrderEntryDto> orderEntries = List.of(orderEntry);
        return OrderRequestDto.builder().orderEntries(orderEntries).customerId("123e4567-e89b-12d3-a456-426614174032")
                .orderStatus(OrderStatus.PERFORMED)
                .build();
    }


    private OrderEntity saveOrderEntity() {
        CustomerEntity customerEntity = saveCustomerEntity();
        ProductEntity productEntity = saveProductEntity();

        OrderEntryEntity orderEntry = OrderEntryEntity.builder()
                .count(2)
                .product(productEntity)
                .build();

        List<OrderEntryEntity> orderEntries = List.of(orderEntry);

        OrderEntity orderEntity = OrderEntity.builder()
                .totalPrice(productEntity.getPrice() * orderEntry.getCount())
                .order_entries(orderEntries)
                .customer(customerEntity)
                .orders_reference(UUID.fromString("123e4567-e89b-12d3-a456-426614174033"))
                .orderStatus(OrderStatus.PERFORMED)
                .build();

        orderEntries.forEach(entry -> entry.setOrder(orderEntity));

        return orderRepository.save(orderEntity);
    }

    private CustomerEntity saveCustomerEntity() {
        return customerRepository.save(CustomerEntity.builder().name("Test")
                .phoneNumber("+09876543210")
                .email("test@gmail.com")
                .address("Test city")
                .customer_reference(UUID.fromString("123e4567-e89b-12d3-a456-426614174032")).build());
    }

    private ProductEntity saveProductEntity() {
        CategoryEntity categoryEntity = createCategory();
        return productRepository.save(ProductEntity.builder().title("Test")
                .description("Galaxy MilkyWay, System Alpha, Zone 123")
                .price(321.2)
                .category(categoryEntity)
                .product_reference(UUID.fromString("123e4567-e89b-12d3-a456-426614174031")).build());
    }


    private CategoryEntity createCategory() {
        return categoryRepository.save(CategoryEntity.builder()
                .title("Test").category_reference(UUID.fromString("123e4567-e89b-12d3-a456-426614174030")).build());
    }



    @Test
    void shouldAddOrder() throws Exception {
        OrderRequestDto orderRequestDto = createOrderEntity();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(orderRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void shouldGetOrderById() throws Exception {
        OrderEntity savedOrder = saveOrderEntity();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/{id}", savedOrder.getOrders_reference()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldGetAllOrders() throws Exception {
        saveOrderEntity();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders")).andExpect(MockMvcResultMatchers.status().isOk());
    }



    @Test
    void shouldDeleteOrder() throws Exception {
        OrderEntity savedOrder = saveOrderEntity();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/orders/{id}", savedOrder.getOrders_reference()));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteOrder_NotFound() throws Exception {
        UUID nonExistentOrderId = UUID.randomUUID();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/orders/{id}", nonExistentOrderId));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldGetNotFoundExceptionWhenOrderDoesNotExist() throws Exception {
        UUID nonExistentOrderId = UUID.fromString("123e4567-e89b-12d3-a456-426614174002");
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/{id}", nonExistentOrderId));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
