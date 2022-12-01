package com.inpost.price.repository;

import com.inpost.price.model.AmountDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmountDiscountRepository extends JpaRepository<AmountDiscount, String> {
}
