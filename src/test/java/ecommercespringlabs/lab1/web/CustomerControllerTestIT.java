package ecommercespringlabs.lab1.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecommercespringlabs.lab1.AbstractIt;
import ecommercespringlabs.lab1.dto.customer.CustomerRequestDto;
import ecommercespringlabs.lab1.repository.CustomerRepository;
import ecommercespringlabs.lab1.repository.entity.CustomerEntity;
import ecommercespringlabs.lab1.service.CustomerService;
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

import java.util.UUID;

@AutoConfigureMockMvc
@SpringBootTest
public class CustomerControllerTestIT extends AbstractIt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @SpyBean
    private CustomerService customerService;

    @BeforeEach
    void init() {
        Mockito.reset(customerService);
        customerRepository.deleteAll();
    }

    private CustomerRequestDto createCustomerDto() {
        return CustomerRequestDto.builder().name("Test")
                .phoneNumber("+09876543210")
                .email("test@gmail.com")
                .address("Testcity").build();
    }
    private CustomerEntity saveCustomerEntity() {
        return customerRepository.save(CustomerEntity.builder().name("Test")
                .phoneNumber("+09876543210")
                .email("test@gmail.com")
                .address("Test city")
                .customer_reference(UUID.fromString("123e4567-e89b-12d3-a456-426614174021")).build());
    }


    @Test
    void shouldAddCustomer() throws Exception {
        CustomerRequestDto customerRequestDto = createCustomerDto();

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(customerRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldAddCustomer_BadRequest() throws Exception {
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder().name("").phoneNumber("+09876543210").email("test@gmail.com").address("Testcity").build();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(customerRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldGetAllCustomers() throws Exception {
        saveCustomerEntity();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers")).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void shouldGetCustomerById() throws Exception {
        CustomerEntity savedCustomer = saveCustomerEntity();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/{id}", savedCustomer.getCustomer_reference()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void shouldUpdateCustomer() throws Exception {
        CustomerEntity savedCustomer = saveCustomerEntity();
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .name("Updated").phoneNumber("+09876543210").email("test@gmail.com").address("Testcity")
                .build();

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/{id}", savedCustomer.getCustomer_reference())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(customerRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateCustomer_NotFound() throws Exception {
        UUID nonExistentCustomerId = UUID.randomUUID();
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .name("Updated Customer Title").phoneNumber("+09876543210").email("test@gmail.com").address("Testcity")
                .build();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/{id}", nonExistentCustomerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(customerRequestDto)));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldUpdateCustomer_BadRequest() throws Exception {
        CustomerEntity savedCustomer = saveCustomerEntity();
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .name("").phoneNumber("+09876543210").email("test@gmail.com").address("Testcity")
                .build();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/{id}", savedCustomer.getCustomer_reference())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(customerRequestDto)));
        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void shouldDeleteCustomer() throws Exception {
        CustomerEntity savedCustomer = saveCustomerEntity();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/{id}", savedCustomer.getCustomer_reference()));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteCustomer_NotFound() throws Exception {
        UUID nonExistentCustomerId = UUID.randomUUID();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/{id}", nonExistentCustomerId));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldGetNotFoundExceptionWhenCustomerDoesNotExist() throws Exception {
        UUID nonExistentCustomerId = UUID.fromString("123e4567-e89b-12d3-a456-426614174022");
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/{id}", nonExistentCustomerId));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
