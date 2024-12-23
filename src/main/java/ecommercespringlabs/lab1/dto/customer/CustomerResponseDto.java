package ecommercespringlabs.lab1.dto.customer;

import lombok.Value;

@Value
public class CustomerResponseDto {
    String id;
    String name;
    String phoneNumber;
    String email;
    String address;
}