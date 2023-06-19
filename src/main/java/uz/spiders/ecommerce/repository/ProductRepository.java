package uz.spiders.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.spiders.ecommerce.entity.Product;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Modifying
    @Query("UPDATE Product p SET p.deleted = true WHERE p.id = :id")
    int softDeleteProductById(@Param("id") UUID id);

    Page<Product> findAllByDeletedFalse(Pageable pageable);
    Optional<Product> findByIdAndDeletedFalse(UUID id);
}
