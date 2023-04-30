package uz.spiders.ecommerce.entity;

import jakarta.persistence.*;
import uz.spiders.ecommerce.entity.enums.Region;
import uz.spiders.ecommerce.entity.template.BaseEntity;

@Entity
public class Address extends BaseEntity {
    @Column(nullable = false)
    @ManyToOne(cascade = CascadeType.MERGE)
    private Region region;
    @Column(nullable = false)
    @ManyToOne(cascade = CascadeType.MERGE)
    private Province province;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String houseNumber;
    private String apartmentNumber;
    private String entranceNumber;
    private String floorNumber;
    private boolean isMain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
