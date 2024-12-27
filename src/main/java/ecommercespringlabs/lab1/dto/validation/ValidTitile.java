package ecommercespringlabs.lab1.dto.validation;

import jakarta.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Payload;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = TitleValidator.class)
@Documented
public @interface ValidTitile {
    String MESSAGE = "Invalid Galactic Address: Address must follow the format: \"Galaxy, System, Zone";

    String message() default MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
