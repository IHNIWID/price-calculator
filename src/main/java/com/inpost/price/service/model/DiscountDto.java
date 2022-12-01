package com.inpost.price.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inpost.price.model.AmountDiscount;
import com.inpost.price.model.PercentageDiscount;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DiscountDto(DiscountType discountType, String discountName, Integer discountPercentage,
                          Integer minimumAmount, Integer productAmountDiscountMultiplier,
                          @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalDateTime expirationDate)
        implements Serializable {

    public static Set<DiscountDto> from(List<AmountDiscount> amountDiscounts, List<PercentageDiscount> percentageDiscounts) {
        var discounts = new HashSet<DiscountDto>();

        for (AmountDiscount amountDiscount : amountDiscounts) {
            discounts.add(
                    buildAmountDiscountDto(amountDiscount)
            );
        }

        for (PercentageDiscount percentageDiscount : percentageDiscounts) {
            discounts.add(
                    buildPercentageDiscountDto(percentageDiscount)
            );
        }
        return discounts;
    }

    private static DiscountDto buildAmountDiscountDto(AmountDiscount amountDiscount) {
        return DiscountDto.builder()
                .discountType(DiscountType.AMOUNT_BASED)
                .discountName(amountDiscount.getDiscountName())
                .discountPercentage(amountDiscount.getDiscountPercentage())
                .minimumAmount(amountDiscount.getMinimumAmount())
                .productAmountDiscountMultiplier(amountDiscount.getProductAmountDiscountMultiplier())
                .expirationDate(amountDiscount.getExpirationDate())
                .build();
    }

    private static DiscountDto buildPercentageDiscountDto(PercentageDiscount percentageDiscount) {
        return DiscountDto.builder()
                .discountType(DiscountType.PERCENTAGE_BASED)
                .discountName(percentageDiscount.getDiscountName())
                .discountPercentage(percentageDiscount.getPercentage())
                .expirationDate(percentageDiscount.getExpirationDate())
                .build();
    }

    public static DiscountDto from(PercentageDiscount discount) {
        return buildPercentageDiscountDto(discount);
    }

    public static DiscountDto from(AmountDiscount discount) {
        return buildAmountDiscountDto(discount);
    }
}
