package uz.spiders.ecommerce.entity;

import jakarta.persistence.Entity;
import lombok.*;
import uz.spiders.ecommerce.entity.template.BaseEntity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Brand extends BaseEntity {
    private String name;
}

