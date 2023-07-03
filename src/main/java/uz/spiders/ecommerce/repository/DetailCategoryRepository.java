package uz.spiders.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.spiders.ecommerce.entity.DetailCategory;

public interface DetailCategoryRepository extends JpaRepository<DetailCategory, Integer> {
}
