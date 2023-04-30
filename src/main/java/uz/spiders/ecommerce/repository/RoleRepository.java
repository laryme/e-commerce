package uz.spiders.ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.spiders.ecommerce.entity.Role;
import uz.spiders.ecommerce.entity.enums.RoleType;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleType(RoleType roleType);

    boolean existsByName(String name);
}
