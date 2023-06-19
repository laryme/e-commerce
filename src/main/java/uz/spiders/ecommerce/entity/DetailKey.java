package uz.spiders.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.spiders.ecommerce.entity.template.BaseEntity;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DetailKey extends BaseEntity {
    private String name;
    private boolean isHeader;
    @ManyToOne
    private DetailCategory detailCategory;
}
