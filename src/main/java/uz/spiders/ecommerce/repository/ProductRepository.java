package uz.spiders.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.spiders.ecommerce.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Modifying
    @Query("DELETE Product WHERE id = :id")
    int deleteProductById(@Param("id") Integer id);

    Page<Product> findAllByIsActiveTrue(PageRequest pageRequest);
}
