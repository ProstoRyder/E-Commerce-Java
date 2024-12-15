package ecommercespringlabs.lab1.utils;

import ecommercespringlabs.lab1.web.FieldsAndReason;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.util.List;

@UtilityClass
public class PaymentDetailsUtils {
    public static ProblemDetail getValidationErrors(List<FieldsAndReason> validationErrors) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Request validation failed");

        problemDetail.setTitle("Field Validation Exception");
        problemDetail.setType(URI.create("field-validation-error"));
        problemDetail.setProperty("invalids Params", validationErrors);

        return problemDetail;
    }
}