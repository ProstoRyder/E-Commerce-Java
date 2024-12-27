package ecommercespringlabs.lab1.service.mapper;

import ecommercespringlabs.lab1.domain.Customer;
import ecommercespringlabs.lab1.dto.customer.CustomerResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class CustomerMapperTest {
    private final CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    private Customer customer;
    private List<Customer> customerList;

    @BeforeEach
    void init() {
        customer = Customer.builder().id(UUID.randomUUID()).name("Test name").phoneNumber("0987654321").email("test@gmail.com").address("testland").build();
        customerList = List.of(customer);
    }

    @Test
    void toCustomerResponseDto() {
        CustomerResponseDto customerResponseDto = customerMapper.toCustomerResponseDto(customer);
        assertNotNull(customerResponseDto);
        assertEquals(customer.getId(), customerResponseDto.getId());
        assertEquals(customer.getName(), customerResponseDto.getName());
    }

    @Test
    void toCustomerResponseDtoList() {
        List<CustomerResponseDto> customerResponseDtoList = customerMapper.toCustomerResponseDtoList(customerList);
        assertNotNull(customerResponseDtoList);
        assertEquals(customerList.size(), customerResponseDtoList.size());
    }

}
