package uz.spiders.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.spiders.ecommerce.entity.ProductDetail;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
}
