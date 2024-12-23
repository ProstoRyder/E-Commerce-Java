package ecommercespringlabs.lab1.repository;

import ecommercespringlabs.lab1.repository.entity.ProductEntity;
import ecommercespringlabs.lab1.repository.projection.ProductDetailsProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends NaturalIdRepository<ProductEntity, UUID>{
    @Query("SELECT p FROM ProductEntity p WHERE p.category.category_reference = :categoryId")
    List<ProductDetailsProjection> findAllByCategoryId(@Param("categoryId") UUID categoryId);
}
