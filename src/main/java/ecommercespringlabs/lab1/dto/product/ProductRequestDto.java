package ecommercespringlabs.lab1.dto.product;

import ecommercespringlabs.lab1.dto.validation.ExtendedValidation;
import ecommercespringlabs.lab1.dto.validation.ValidTitile;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@GroupSequence({ProductRequestDto.class, ExtendedValidation.class})
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductRequestDto {
    @NotBlank(message = "Title is required.")
    @Size(max = 50, message = "Title must not exceed 50 characters.")
    String title;

    @NotBlank(message = "Description is required.")
    @Size(max = 255, message = "Description must not exceed 255 characters.")
    @ValidTitile(groups = ExtendedValidation.class)
    String description;

    @NotNull(message = "Price is required.")
    Double price;

    @NotNull(message = "Category ID is required.")
    String categoryId;
}