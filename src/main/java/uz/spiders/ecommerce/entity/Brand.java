package uz.spiders.ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.spiders.ecommerce.entity.template.BaseEntity;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Brand extends BaseEntity {
    @Column(unique = true)
    private String name;
    private String logoUrl;
}

