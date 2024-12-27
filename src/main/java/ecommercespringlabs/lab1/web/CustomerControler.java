package ecommercespringlabs.lab1.web;

import ecommercespringlabs.lab1.dto.customer.CustomerResponseDto;
import ecommercespringlabs.lab1.service.CustomerService;
import ecommercespringlabs.lab1.service.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerControler {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerDetailsById(@PathVariable UUID id) {
        return ResponseEntity.ok(customerMapper.toCustomerResponseDto(customerService.findCustomerDetailsById(id.toString())));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCategories() {
        return ResponseEntity.ok(customerMapper.toCustomerResponseDtoList(customerService.findAllCustomer()));
    }
}