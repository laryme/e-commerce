package uz.spiders.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.spiders.ecommerce.entity.template.BaseEntity;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {
    private String name;
    private Double price;
    private String description;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Category category;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<ProductDetail> productDetails;

    @OneToMany(cascade = CascadeType.MERGE)
    private List<ProductPicture> productPictures;

}
