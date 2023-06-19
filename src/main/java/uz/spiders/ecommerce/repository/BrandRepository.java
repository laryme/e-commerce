package uz.spiders.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.spiders.ecommerce.entity.Brand;

import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
}
