package uz.spiders.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.spiders.ecommerce.entity.DetailKey;

@Repository
public interface DetailKeyRepository extends JpaRepository<DetailKey, Integer> {
}
