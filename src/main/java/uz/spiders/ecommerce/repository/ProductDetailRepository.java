package uz.spiders.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.spiders.ecommerce.entity.ProductDetail;

import java.util.UUID;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, UUID> {
}
