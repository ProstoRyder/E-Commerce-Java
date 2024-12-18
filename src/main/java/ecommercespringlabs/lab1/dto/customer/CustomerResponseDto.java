package ecommercespringlabs.lab1.dto.customer;

import lombok.Value;

@Value
public class CustomerResponseDto {
    Long id;
    String name;
    String phoneNumber;
    String email;
    String address;
}