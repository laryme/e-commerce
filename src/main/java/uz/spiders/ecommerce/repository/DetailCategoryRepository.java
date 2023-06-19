package uz.spiders.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.spiders.ecommerce.entity.DetailCategory;

import java.util.UUID;

public interface DetailCategoryRepository extends JpaRepository<DetailCategory, UUID> {
}
