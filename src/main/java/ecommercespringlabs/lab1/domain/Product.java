package ecommercespringlabs.lab1.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    String id;
    String title;
    String description;
    Double price;
    Category category;
}