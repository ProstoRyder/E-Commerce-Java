package ecommercespringlabs.lab1.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
@Builder(toBuilder = true)
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_customer_seq")
    @SequenceGenerator(name = "id_customer_seq", sequenceName = "id_customer_seq")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "email")
    String email;

    @Column(name = "address")
    String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderEntity> orders;

    @NaturalId
    @Column(name = "customer_reference", unique = true, nullable = false)
    UUID customer_reference;
}
