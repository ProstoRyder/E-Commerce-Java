package ecommercespringlabs.lab1.service.mapper;

import ecommercespringlabs.lab1.domain.Customer;
import ecommercespringlabs.lab1.dto.customer.CustomerResponseDto;
import ecommercespringlabs.lab1.repository.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerResponseDto toCustomerResponseDto(Customer customer);
    List<CustomerResponseDto> toCustomerResponseDtoList(List<Customer> customer);
    @Mapping(source = "customer_reference", target = "id")
    Customer toCustomer(CustomerEntity customerEntity);
    List<Customer> toCustomerList(Iterable<CustomerEntity> customerEntities);
}