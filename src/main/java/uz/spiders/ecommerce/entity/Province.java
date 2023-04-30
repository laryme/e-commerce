package uz.spiders.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import uz.spiders.ecommerce.entity.enums.Region;
import uz.spiders.ecommerce.entity.template.BaseEntity;

@Entity
public class Province extends BaseEntity {
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province", referencedColumnName = "id")
    private Region region;
}
