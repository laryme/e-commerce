package uz.spiders.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.spiders.ecommerce.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
