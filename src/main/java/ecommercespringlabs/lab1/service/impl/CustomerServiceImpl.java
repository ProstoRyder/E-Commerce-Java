package ecommercespringlabs.lab1.service.impl;

import ecommercespringlabs.lab1.domain.Customer;
import ecommercespringlabs.lab1.dto.customer.CustomerRequestDto;
import ecommercespringlabs.lab1.repository.CustomerRepository;
import ecommercespringlabs.lab1.repository.entity.CustomerEntity;
import ecommercespringlabs.lab1.service.CustomerService;
import ecommercespringlabs.lab1.service.exception.CustomerNotFoundException;
import ecommercespringlabs.lab1.service.mapper.CustomerMapper;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;


    @Override
    @Transactional
    public Customer addCustomer(CustomerRequestDto customerRequestDto) {
        CustomerEntity newCustomer = CustomerEntity.builder()
                .name(customerRequestDto.getName())
                .phoneNumber(customerRequestDto.getPhoneNumber())
                .address(customerRequestDto.getAddress())
                .email(customerRequestDto.getEmail())
                .customer_reference(UUID.randomUUID()).build();
        try {
            return customerMapper.toCustomer(customerRepository.save(newCustomer));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAllCustomer() {
        return customerMapper.toCustomerList(customerRepository.findAll());
    }

    @Override
    @Transactional
    public Customer findCustomerDetailsById(UUID id) {
        CustomerEntity customer = customerRepository.findByNaturalId(id)
                .orElseThrow(() -> new CustomerNotFoundException(id.toString()));
        return customerMapper.toCustomer(customer);
    }


    @Override
    @Transactional
    public void deleteCustomer(UUID id) {
        findCustomerDetailsById(id);
        try {
            customerRepository.deleteByNaturalId(id);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }


    @Override
    public Customer updateCustomer(CustomerRequestDto customerRequestDto, UUID id) {
        CustomerEntity customer = customerRepository.findByNaturalId(id).orElseThrow(() -> new CustomerNotFoundException(id.toString()));
        customer.setCustomer_reference(id);

        customer.setName(customerRequestDto.getName());
        customer.setPhoneNumber(customerRequestDto.getPhoneNumber());
        customer.setAddress(customerRequestDto.getAddress());
        customer.setEmail(customerRequestDto.getEmail());

        try {
            return customerMapper.toCustomer(customerRepository.save(customer));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}
