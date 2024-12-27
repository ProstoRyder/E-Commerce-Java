package ecommercespringlabs.lab1.dto.customer;

import lombok.Value;

import java.util.UUID;

@Value
public class CustomerResponseDto {
    UUID id;
    String name;
    String phoneNumber;
    String email;
    String address;
}