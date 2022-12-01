package com.inpost.price.api.model;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

public record PercentageDiscountRequest(@NotBlank String discountName,
                                        @NotNull @Min(value = 1) @Max(value = 100) Integer discountPercentage,
                                        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalDateTime expirationDate,
                                        @NotEmpty Set<String> productIds) {


}
