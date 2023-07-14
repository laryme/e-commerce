package uz.spiders.ecommerce.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.spiders.ecommerce.entity.*;
import uz.spiders.ecommerce.entity.enums.Permission;
import uz.spiders.ecommerce.entity.enums.RoleType;
import uz.spiders.ecommerce.repository.*;

import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final DetailCategoryRepository detailCategoryRepository;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String flag;

    @Override
    public void run(String... args) throws Exception {
        if (Objects.equals(flag, "create") || Objects.equals(flag, "create-drop")) {
            Role admin = roleRepository.save(new Role(
                    "ADMIN",
                    RoleType.ADMIN,
                    Set.of(Permission.values())
            ));

            roleRepository.save(new Role(
                    "USER",
                    RoleType.USER,
                    Set.of(
                            Permission.CATEGORY_ONE,
                            Permission.CATEGORY_LIST)
            ));

            User superUser = new User("lochinbek433@gmail.com",
                    passwordEncoder.encode("lary2002#"),
                    admin);
            superUser.setActive(true);

            userRepository.save(superUser);

            brandRepository.save(new Brand("BRAND-1", "/home/lary/resources/ecommerce/images/brand/logo-ee013b49-45fe-43e1-8b97-d5837594e2f9"));
            categoryRepository.save(new Category("CATEGORY-1", null));

            detailCategoryRepository.save(new DetailCategory("DETAIL-CATEGORY-1"));
        }
    }
}
