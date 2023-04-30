package uz.spiders.ecommerce.entity;

import jakarta.persistence.*;
import uz.spiders.ecommerce.entity.enums.CardType;
import uz.spiders.ecommerce.entity.template.BaseEntity;

@Entity
public class BankCard extends BaseEntity {
    private String cardNumber;
    private String cardExpiry;
    private CardType cardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
