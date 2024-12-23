package ecommercespringlabs.lab1.repository;

import ecommercespringlabs.lab1.repository.entity.CustomerEntity;

import java.util.UUID;

public interface CustomerRepository extends NaturalIdRepository<CustomerEntity, UUID>{
}
