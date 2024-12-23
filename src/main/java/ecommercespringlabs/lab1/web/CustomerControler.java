package ecommercespringlabs.lab1.web;

import ecommercespringlabs.lab1.domain.Customer;
import ecommercespringlabs.lab1.dto.customer.CustomerRequestDto;
import ecommercespringlabs.lab1.dto.customer.CustomerResponseDto;
import ecommercespringlabs.lab1.service.CustomerService;
import ecommercespringlabs.lab1.service.mapper.CustomerMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerControler {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable UUID customerId) {
        return ResponseEntity.ok(customerMapper.toCustomerResponseDto(customerService.findCustomerDetailsById(customerId)));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomer() {
        return ResponseEntity.ok(customerMapper.toCustomerResponseDtoList(customerService.findAllCustomer()));
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> addCustomer(@RequestBody @Valid CustomerRequestDto customerRequestDto) {
        return ResponseEntity.ok(customerMapper.toCustomerResponseDto(customerService.addCustomer(customerRequestDto)));
    }
    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable UUID customerId) {
        customerService.deleteCustomer(customerId);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@RequestBody @Valid CustomerRequestDto customerRequestDto, @PathVariable UUID customerId) {
        Customer customer = customerService.updateCustomer(customerRequestDto, customerId);
        return ResponseEntity.ok(customerMapper.toCustomerResponseDto(customer));
    }
}