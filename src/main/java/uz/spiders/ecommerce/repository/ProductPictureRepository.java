package uz.spiders.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.spiders.ecommerce.entity.ProductPicture;

import java.util.UUID;

public interface ProductPictureRepository extends JpaRepository<ProductPicture, UUID> {
}
