package uz.spiders.ecommerce.payload;


import jakarta.validation.constraints.NotBlank;
import uz.spiders.ecommerce.entity.Role;

import java.util.List;
import java.util.stream.Collectors;

public record RoleDTO(
        @NotBlank(message = "Role name is required")
        String name,

        List<Integer> permissions
) {
        public RoleDTO(Role role) {
                this(role.getName(), role.getPermissionSet().stream()
                        .map(Enum::ordinal)
                        .collect(Collectors.toList()));
        }
}
