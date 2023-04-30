package uz.spiders.ecommerce.entity.enums;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import uz.spiders.ecommerce.entity.Province;
import uz.spiders.ecommerce.entity.template.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Region extends BaseEntity {
    private String name;
    @OneToMany(mappedBy = "region", cascade = CascadeType.MERGE)
    private List<Province> provinces = new ArrayList<>();
}
