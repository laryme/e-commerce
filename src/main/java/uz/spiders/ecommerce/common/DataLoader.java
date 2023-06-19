package uz.spiders.ecommerce.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.spiders.ecommerce.entity.Category;
import uz.spiders.ecommerce.entity.Role;
import uz.spiders.ecommerce.entity.User;
import uz.spiders.ecommerce.entity.enums.Permission;
import uz.spiders.ecommerce.entity.enums.RoleType;
import uz.spiders.ecommerce.repository.BrandRepository;
import uz.spiders.ecommerce.repository.CategoryRepository;
import uz.spiders.ecommerce.repository.RoleRepository;
import uz.spiders.ecommerce.repository.UserRepository;

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


        }
    }
}
