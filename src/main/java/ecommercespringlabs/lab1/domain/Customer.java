package ecommercespringlabs.lab1.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Customer {
    String id;
    String name;
    String phoneNumber;
    String email;
    String address;
}
