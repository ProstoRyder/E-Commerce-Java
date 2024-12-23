package ecommercespringlabs.lab1.repository;

import ecommercespringlabs.lab1.repository.entity.CategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends NaturalIdRepository<CategoryEntity, UUID> {
}
