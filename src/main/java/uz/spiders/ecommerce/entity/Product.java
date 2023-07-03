package uz.spiders.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uz.spiders.ecommerce.entity.template.BaseEntity;

import java.sql.Timestamp;
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

    private boolean isActive;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedAt;
}