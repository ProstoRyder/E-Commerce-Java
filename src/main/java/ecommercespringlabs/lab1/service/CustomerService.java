package ecommercespringlabs.lab1.service;

import ecommercespringlabs.lab1.domain.Customer;
import ecommercespringlabs.lab1.dto.customer.CustomerRequestDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    Customer findCustomerDetailsById(UUID id);
    List<Customer> findAllCustomer();
    Customer addCustomer(CustomerRequestDto customerRequestDto);
    void deleteCustomer(UUID id);
    Customer updateCustomer(CustomerRequestDto customerRequestDto, UUID id);
}
