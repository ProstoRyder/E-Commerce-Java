package ecommercespringlabs.lab1.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders_entry")
@Builder(toBuilder = true)
public class OrderEntryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_orders_entry_seq")
    @SequenceGenerator(name = "id_orders_entry_seq", sequenceName = "id_orders_entry_seq")
    Long id;

    @Column(name = "quantity")
    Integer count;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    ProductEntity product;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = "order_id", nullable = false)
    OrderEntity order;

}