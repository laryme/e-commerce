package uz.spiders.ecommerce.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.spiders.ecommerce.entity.template.BaseEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailCategory extends BaseEntity {
    private String name;
}
