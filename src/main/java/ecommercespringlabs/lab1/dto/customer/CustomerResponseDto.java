package ecommercespringlabs.lab1.dto.customer;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CustomerResponseDto {
    Long id;
    String name;
    String phoneNumber;
    String email;
    String address;
}
