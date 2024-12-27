package ecommercespringlabs.lab1.service.impl;

import ecommercespringlabs.lab1.domain.Customer;
import ecommercespringlabs.lab1.service.CustomerService;
import ecommercespringlabs.lab1.service.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    List<Customer> customers = new ArrayList<>();

    public CustomerServiceImpl() {
        customers.add(Customer.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).name("Mors").phoneNumber("0321893231").email("morsik@gmail.com").address("skyland").build());
        customers.add(Customer.builder().id(UUID.randomUUID()).name("Tom").phoneNumber("54843954385").email("cattomas@gmail.com").address("dreamland").build());
    }

    @Override
    public Customer findCustomerDetailsById(String id) {
        return customers.stream().filter(customer -> customer.getId().equals(UUID.fromString(id)))
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }


    @Override
    public List<Customer> findAllCustomer() {
        return customers;
    }


}
