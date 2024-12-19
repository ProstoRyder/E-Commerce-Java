package ecommercespringlabs.lab1.domain;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CosmoCat {
    UUID id;
    String name;
    String breed;
    String gender;
}
