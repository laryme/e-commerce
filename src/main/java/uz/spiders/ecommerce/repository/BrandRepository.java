package uz.spiders.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.spiders.ecommerce.entity.Brand;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query("SELECT logoUrl FROM Brand WHERE id = :id")
    Optional<String> getBrandLogoById(@Param(value = "id") Integer id);
}
