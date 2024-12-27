package ecommercespringlabs.lab1.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecommercespringlabs.lab1.domain.Customer;
import ecommercespringlabs.lab1.service.CustomerService;
import ecommercespringlabs.lab1.service.exception.CustomerNotFoundException;
import ecommercespringlabs.lab1.service.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class CustomerControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerMapper customerMapper;

    private Customer customer;

    @BeforeEach
    public void init() {
        customer = Customer.builder().id(UUID.randomUUID()).name("Barsik").phoneNumber("052993231").email("jnjksdf@gmail.com").address("underland").build();
    }

    @Test
    public void shouldGetAllCustomers() throws Exception {
        List<Customer> response = List.of(customer);
        Mockito.when(customerService.findAllCustomer()).thenReturn(response);

        mockMvc.perform(get("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customerMapper.toCustomerResponseDtoList(response))));
    }

    @Test
    public void shouldGetCustomerById() throws Exception {
        Mockito.when(customerService.findCustomerDetailsById(anyString())).thenReturn(customer);

        mockMvc.perform(get("/api/v1/customers/{id}", customer.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customerMapper.toCustomerResponseDto(customer))));
    }



    @Test
    public void shouldGetNotFoundExceptionWhenCustomerDoesNotExist() throws Exception {
        String invalidId = UUID.randomUUID().toString();
        Mockito.when(customerService.findCustomerDetailsById(invalidId)).thenThrow(new CustomerNotFoundException(invalidId));
        mockMvc.perform(get("/api/v1/customers/{customerId}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
