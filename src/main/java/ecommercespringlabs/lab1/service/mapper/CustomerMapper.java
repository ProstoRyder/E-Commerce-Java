package ecommercespringlabs.lab1.service.mapper;

import ecommercespringlabs.lab1.domain.Customer;
import ecommercespringlabs.lab1.dto.customer.CustomerResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerResponseDto toCustomerResponseDto(Customer customer);
    List<CustomerResponseDto> toCustomerResponseDtoList(List<Customer> customer);
}