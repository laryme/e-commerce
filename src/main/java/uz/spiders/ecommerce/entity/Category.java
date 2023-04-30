package uz.spiders.ecommerce.entity;

import jakarta.persistence.*;
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
public class Category extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @OneToOne
    private Category parentCategory;

}
