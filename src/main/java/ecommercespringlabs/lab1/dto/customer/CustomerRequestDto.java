package ecommercespringlabs.lab1.dto.customer;

import ecommercespringlabs.lab1.dto.validation.ExtendedValidation;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@GroupSequence({ CustomerRequestDto.class, ExtendedValidation.class})
public class CustomerRequestDto {
    @NotBlank(message = "Name is required.")
    @Size(max = 30, message = "Name must not exceed 99 characters.")
    String name;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be in a valid format, e.g., +1234567890.")
    String phoneNumber;

    @NotBlank(message = "Email is required.")
    @Email(message = "Please provide a valid email address.")
    String email;

    @NotBlank(message = "Address is required.")
    @Email(message = "Please provide address.")
    String address;
}
