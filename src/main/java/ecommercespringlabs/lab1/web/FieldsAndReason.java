package ecommercespringlabs.lab1.web;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class FieldsAndReason {
    String field;
    String reason;
}
