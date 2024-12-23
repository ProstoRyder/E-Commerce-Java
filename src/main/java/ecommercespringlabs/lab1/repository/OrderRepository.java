package ecommercespringlabs.lab1.repository;

import ecommercespringlabs.lab1.repository.entity.OrderEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends NaturalIdRepository<OrderEntity, UUID>{
}
