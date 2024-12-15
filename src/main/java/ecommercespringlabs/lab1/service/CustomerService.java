package ecommercespringlabs.lab1.service;

import ecommercespringlabs.lab1.domain.Customer;

import java.util.List;

public interface CustomerService {
    Customer findCustomerDetailsById(long customerId);
    List<Customer> findAllCustomer();
}
