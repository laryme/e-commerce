package uz.spiders.ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.spiders.ecommerce.entity.Category;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
