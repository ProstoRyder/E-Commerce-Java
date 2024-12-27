package ecommercespringlabs.lab1.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;


public class TitleValidator implements ConstraintValidator<ValidTitile, String>{

        private static final String GALACTIC_ADDRESS_REGEX = "^Galaxy \\w+, System [A-Za-z]+, Zone \\d+";

        private static final Pattern pattern = Pattern.compile(GALACTIC_ADDRESS_REGEX);

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return pattern.matcher(value).matches();
        }
}
