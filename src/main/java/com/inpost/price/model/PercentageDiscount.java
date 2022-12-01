package com.inpost.price.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PercentageDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String discountName;
    private Integer percentage;
    private LocalDateTime expirationDate;
    private LocalDateTime createdDate;

    @ManyToMany
    @JoinTable(
            name = "ProductDiscount",
            joinColumns = @JoinColumn(name = "discount_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PercentageDiscount that = (PercentageDiscount) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
