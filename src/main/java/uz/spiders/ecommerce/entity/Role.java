package uz.spiders.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.spiders.ecommerce.entity.enums.Permission;
import uz.spiders.ecommerce.entity.enums.RoleType;
import uz.spiders.ecommerce.entity.template.BaseEntity;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @ElementCollection
    @CollectionTable(name = "role_permission",
            joinColumns = {
                @JoinColumn(name = "role_id")},
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = {"role_id", "permission"})})
    @Column(name = "permission")
    @Enumerated(EnumType.STRING)
    private Set<Permission> permissionSet;
}
