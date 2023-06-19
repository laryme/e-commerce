package uz.spiders.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.spiders.ecommerce.entity.template.BaseEntity;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
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
