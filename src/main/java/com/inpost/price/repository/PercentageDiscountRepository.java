package com.inpost.price.repository;

import com.inpost.price.model.PercentageDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PercentageDiscountRepository extends JpaRepository<PercentageDiscount, String> {
}
