package ecommercespringlabs.lab1.domain;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Customer {
    UUID id;
    String name;
    String phoneNumber;
    String email;
    String address;
}
