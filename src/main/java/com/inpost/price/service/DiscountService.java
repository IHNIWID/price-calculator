package com.inpost.price.service;

import com.inpost.price.api.model.AmountDiscountRequest;
import com.inpost.price.api.model.PercentageDiscountRequest;
import com.inpost.price.model.AmountDiscount;
import com.inpost.price.model.PercentageDiscount;
import com.inpost.price.repository.AmountDiscountRepository;
import com.inpost.price.repository.PercentageDiscountRepository;
import com.inpost.price.repository.ProductRepository;
import com.inpost.price.service.model.DiscountDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final PercentageDiscountRepository percentageDiscountRepository;
    private final AmountDiscountRepository amountDiscountRepository;

    private final ProductRepository productRepository;

    @Transactional
    public Set<DiscountDto> getAvailableDiscounts() {
        return DiscountDto.from(amountDiscountRepository.findAll(), percentageDiscountRepository.findAll());
    }
    @Transactional
    public Optional<DiscountDto> createPercentageDiscount(PercentageDiscountRequest percentageDiscountRequest) {
        var products = new HashSet<>(productRepository.findAllById(percentageDiscountRequest.productIds()));

        var discount = PercentageDiscount.builder()
                .createdDate(LocalDateTime.now())
                .expirationDate(percentageDiscountRequest.expirationDate())
                .discountName(percentageDiscountRequest.discountName())
                .percentage(percentageDiscountRequest.discountPercentage())
                .products(products)
                .build();

        return Optional.ofNullable(DiscountDto.from(percentageDiscountRepository.save(discount)));
    }
    @Transactional
    public Optional<DiscountDto> createAmountDiscount(AmountDiscountRequest amountDiscountRequest) {
        var products = new HashSet<>(productRepository.findAllById(amountDiscountRequest.productIds()));

        var discount = AmountDiscount.builder()
                .createdDate(LocalDateTime.now())
                .expirationDate(amountDiscountRequest.expirationDate())
                .discountName(amountDiscountRequest.discountName())
                .minimumAmount(amountDiscountRequest.minimumAmount())
                .discountPercentage(amountDiscountRequest.discountPercentage())
                .productAmountDiscountMultiplier(amountDiscountRequest.productAmountDiscountMultiplier())
                .products(products)
                .build();

        return Optional.ofNullable(DiscountDto.from(amountDiscountRepository.save(discount)));
    }
}
