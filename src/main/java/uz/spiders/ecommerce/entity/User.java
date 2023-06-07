package uz.spiders.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.spiders.ecommerce.entity.enums.Gender;
import uz.spiders.ecommerce.entity.template.BaseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {

    /*@Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;*/

    @Column(nullable = true, columnDefinition = "VARCHAR(50) DEFAULT 'user-not-defined'")
    private String fullName = "user-"+"userId";

    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private String password;
    private Gender gender;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<PhoneNumber> phoneNumber = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.MERGE)
    private PassportDetail passportDetail;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<BankCard> bankCards = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Address> addresses = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    private boolean isActive;
    private boolean isNonDeleted = true;
    private boolean isNonBlocked = true;

    public User(String email, String password, Role role) {
        this.password = password;
        this.email = email;
        this.roles = Set.of(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> role.getPermissionSet().stream())
                .collect(Collectors.toSet());
    }

    public void setRoles(Role role) {
        this.roles = Set.of(role);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isNonDeleted;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNonBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}