package uz.spiders.ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.spiders.ecommerce.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
