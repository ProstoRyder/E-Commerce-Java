package ecommercespringlabs.lab1.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
@Builder(toBuilder = true)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_category_seq")
    @SequenceGenerator(name = "id_category_seq", sequenceName = "id_category_seq")
    Long id;

    @Column(name = "title", unique = true, nullable = false)
    String title;

    @NaturalId
    @Column(name = "category_reference", unique = true, nullable = false)
    UUID category_reference;
}
