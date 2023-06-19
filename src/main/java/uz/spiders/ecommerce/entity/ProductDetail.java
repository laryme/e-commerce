package uz.spiders.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.spiders.ecommerce.entity.template.BaseEntity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail extends BaseEntity {
    @ManyToOne
    private DetailKey detailKey;
    private String value;
}