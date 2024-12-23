package ecommercespringlabs.lab1.repository.entity;

import ecommercespringlabs.lab1.common.OrderStatus;
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
@Table(name = "orders")
@Builder(toBuilder = true)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_orders_seq")
    @SequenceGenerator(name = "id_product_seq", sequenceName = "id_orders_seq")
    Long id;

    @Column(name = "total_price")
    Double totalPrice;

    @Column(name = "orders_status")
    @Enumerated(EnumType.ORDINAL)
    OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = true)
    List<OrderEntryEntity> order_entries;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    CustomerEntity customer;

    @NaturalId
    @Column(name = "orders_reference", unique = true, nullable = false)
    UUID orders_reference;
}
