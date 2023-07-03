package uz.spiders.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.spiders.ecommerce.entity.ProductPicture;

import java.util.Optional;

public interface ProductPictureRepository extends JpaRepository<ProductPicture, Integer> {
    Optional<ProductPicture> findByFileName(String name);
}
