package com.inpost.price.api;

import com.inpost.price.api.model.AmountDiscountRequest;
import com.inpost.price.api.model.PercentageDiscountRequest;
import com.inpost.price.service.DiscountService;
import com.inpost.price.service.PriceService;
import com.inpost.price.service.model.DiscountDto;
import com.inpost.price.service.model.ProductDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/price")
@RequiredArgsConstructor
@Validated
public class PriceController {

    private final PriceService priceService;
    private final DiscountService discountService;

    @GetMapping(path = "")
    public ResponseEntity<ProductDto> getPrice(@RequestParam @NotBlank String id, @RequestParam @Min(1) @Max(2000) Integer amount) {
        log.info("Get price for product - id: {}, and amount: {}", id, amount);
        return priceService.calculatePrice(id, amount)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/amountDiscount")
    public ResponseEntity<DiscountDto> postAmountDiscount(@RequestBody AmountDiscountRequest amountDiscountRequest) {
        return discountService.createAmountDiscount(amountDiscountRequest)
                .map(discount -> ResponseEntity.accepted().body(discount))
                .orElse(ResponseEntity.unprocessableEntity().build());
    }

    @PostMapping(path = "/percentageDiscount")
    public ResponseEntity<DiscountDto> postPercentageDiscount(@RequestBody PercentageDiscountRequest percentageDiscountRequest) {
        return discountService.createPercentageDiscount(percentageDiscountRequest)
                .map(discount -> ResponseEntity.accepted().body(discount))
                .orElse(ResponseEntity.unprocessableEntity().build());
    }

    @GetMapping(path = "/discounts")
    public ResponseEntity<Set<DiscountDto>> getDiscounts() {
        log.info("Get available discounts");
        var discounts = discountService.getAvailableDiscounts();
        return discounts.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(discounts);
    }


}
