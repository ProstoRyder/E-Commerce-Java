package ecommercespringlabs.lab1.service;

import ecommercespringlabs.lab1.domain.Customer;
import ecommercespringlabs.lab1.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {CustomerServiceImpl.class})
public class CustomerServiceTest {

    @MockBean
    private CustomerService customerService;
    private Customer customer;

    @BeforeEach
    public void init() {
        customer = Customer.builder().id(1L).name("Barsik").phoneNumber("052993231").email("jnjksdf@gmail.com").address("underland").build();
    }

    @Test
    void getCustomers() {
        List<Customer> mockCustomers = List.of(customer);
        Mockito.when(customerService.findAllCustomer()).thenReturn(mockCustomers);
        assertNotNull(customerService.findAllCustomer());
    }

    @Test
    void findCustomerById() {
        long id = customer.getId();
        Mockito.when(customerService.findCustomerDetailsById(id)).thenReturn(customer);
        assertNotNull(customerService.findCustomerDetailsById(id));
    }

}
