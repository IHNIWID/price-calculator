package com.inpost.price.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.inpost.price.model.AmountDiscount;
import com.inpost.price.model.PercentageDiscount;
import com.inpost.price.model.Product;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductDto(String productId, String productName,
                         @JsonFormat(shape = JsonFormat.Shape.STRING) BigDecimal oneProductPrice,
                         @JsonFormat(shape = JsonFormat.Shape.STRING) BigDecimal overallPrice,
                         Integer amount) implements Serializable {

    public static ProductDto from(Product product, Integer amount) {
        return ProductDto.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .oneProductPrice(product.getPrice())
                .overallPrice(amount > 1 ? calculatePrice(product, amount) : product.getPrice())
                .amount(amount)
                .build();
    }

    public static ProductDto from(Product product) {
        return ProductDto.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .oneProductPrice(product.getPrice())
                .build();
    }

    private static BigDecimal calculatePrice(Product product, Integer amount) {
        var newPrice = product.getPrice().multiply(BigDecimal.valueOf(amount));

        if (hasValidAmountDiscounts(product.getAmountDiscounts())) {
            for (AmountDiscount amountDiscount : product.getAmountDiscounts()) {
                if (amountDiscount.getMinimumAmount() <= amount) {
                    var discountValue = newPrice.multiply(BigDecimal.valueOf(Double.valueOf(amountDiscount.getDiscountPercentage()) / 100)
                            .setScale(2, RoundingMode.HALF_EVEN));
                    var discountMultiplier = amountDiscount.getProductAmountDiscountMultiplier() == 0
                            ? BigDecimal.ONE
                            : new BigDecimal((amount - amountDiscount.getMinimumAmount()) / amountDiscount.getProductAmountDiscountMultiplier())
                            .setScale(0, RoundingMode.DOWN).add(BigDecimal.valueOf(1.0));
                    newPrice = newPrice.subtract(discountValue.multiply(discountMultiplier));
                }
            }
        }

        if (hasValidPercentageDiscounts(product.getPercentageDiscounts())) {
            for (PercentageDiscount percentageDiscount : product.getPercentageDiscounts()) {
                var discountValue = newPrice.multiply(BigDecimal.valueOf(Double.valueOf(percentageDiscount.getPercentage()) / 100)
                        .setScale(2, RoundingMode.HALF_EVEN));
                newPrice = newPrice.subtract(discountValue);
            }
        }

        return newPrice.setScale(2, RoundingMode.HALF_EVEN);
    }

    private static boolean hasValidPercentageDiscounts(Set<PercentageDiscount> percentageDiscounts) {
        return !percentageDiscounts.isEmpty() &&
                percentageDiscounts.stream()
                        .anyMatch(percentageDiscount -> percentageDiscount.getExpirationDate().isAfter(LocalDateTime.now()));
    }

    private static boolean hasValidAmountDiscounts(Set<AmountDiscount> amountDiscounts) {
        return !amountDiscounts.isEmpty() &&
                amountDiscounts.stream()
                        .anyMatch(amountDiscount -> amountDiscount.getExpirationDate().isAfter(LocalDateTime.now()));
    }

}
