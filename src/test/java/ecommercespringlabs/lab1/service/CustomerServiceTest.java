package ecommercespringlabs.lab1.service;

import ecommercespringlabs.lab1.domain.Customer;
import ecommercespringlabs.lab1.dto.customer.CustomerRequestDto;
import ecommercespringlabs.lab1.dto.customer.CustomerResponseDto;
import ecommercespringlabs.lab1.repository.CustomerRepository;
import ecommercespringlabs.lab1.repository.entity.CustomerEntity;
import ecommercespringlabs.lab1.service.impl.CustomerServiceImpl;
import ecommercespringlabs.lab1.service.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CustomerServiceImpl.class})
public class CustomerServiceTest {


    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerService customerService;

    private UUID id;
    private CustomerRequestDto customerRequestDto;
    private CustomerResponseDto customerResponseDto;
    private CustomerEntity customerEntity;
    private Customer customer;

    @BeforeEach
    public void init() {
        id = UUID.randomUUID();
        customer = Customer.builder().id(id.toString())
                .name("Barsik")
                .phoneNumber("052993231")
                .email("jnjksdf@gmail.com")
                .address("underland").build();
        customerEntity = CustomerEntity.builder()
                .name("Barsik")
                .phoneNumber("052993231")
                .email("jnjksdf@gmail.com")
                .address("underland").build();
        customerRequestDto = CustomerRequestDto.builder()
                .name("Barsik")
                .phoneNumber("052993231")
                .email("jnjksdf@gmail.com")
                .address("underland").build();
    }

    @Test
    void findAllCustomers_Success() {
        List<CustomerEntity> customerEntities = new ArrayList<>(List.of(customerEntity));
        List<Customer> customers = new ArrayList<>(List.of(customer));

        when(customerRepository.findAll()).thenReturn(customerEntities);
        when(customerMapper.toCustomerList(customerEntities)).thenReturn(customers);

        List<Customer> result = customerService.findAllCustomer();

        assertNotNull(result);
    }

    @Test
    void findCustomerById_Success() {
        when(customerRepository.findByNaturalId(id)).thenReturn(Optional.of(customerEntity));
        when(customerMapper.toCustomer(customerEntity)).thenReturn(customer);

        Customer result = customerService.findCustomerDetailsById(id);

        assertNotNull(result);
    }

    @Test
    void addCustomer_Success() {
        CustomerEntity newCustomer = CustomerEntity.builder()
                .name(customerRequestDto.getName())
                .customer_reference(UUID.randomUUID())
                .build();

        when(customerRepository.save(Mockito.any(CustomerEntity.class))).thenReturn(newCustomer);
        when(customerMapper.toCustomer(Mockito.any(CustomerEntity.class))).thenReturn(customer);

        Customer result = customerService.addCustomer(customerRequestDto);

        assertNotNull(result);
    }

    @Test
    void deleteCustomer_Success() {
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findByNaturalId(customerId)).thenReturn(Optional.of(customerEntity));
        Mockito.doNothing().when(customerRepository).deleteByNaturalId(customerId);
        customerService.deleteCustomer(customerId);
        Mockito.verify(customerRepository, Mockito.times(1)).deleteByNaturalId(customerId);
    }


    @Test
    void updateCustomer_Success() {
        when(customerRepository.findByNaturalId(id)).thenReturn(Optional.of(customerEntity));
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);
        when(customerMapper.toCustomer(customerEntity)).thenReturn(customer);
        Customer result = customerService.updateCustomer(customerRequestDto, id);
    }
}
