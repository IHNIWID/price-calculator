package com.inpost.price.service;

import com.inpost.price.exception.InvalidUUIDException;
import com.inpost.price.repository.AmountDiscountRepository;
import com.inpost.price.repository.PercentageDiscountRepository;
import com.inpost.price.repository.ProductRepository;
import com.inpost.price.service.model.ProductDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final ProductRepository productRepository;
    private final PercentageDiscountRepository percentageDiscountRepository;
    private final AmountDiscountRepository amountDiscountRepository;

    @Transactional
    public Optional<ProductDto> calculatePrice(String id, Integer amount) {
        try {
            var uuid = UUID.fromString(id);

        } catch (IllegalArgumentException illegalArgumentException) {
            throw new InvalidUUIDException(id);
        }
        var product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id: [" + id +"] not found."));


        return Optional.of(ProductDto.from(product, amount));
    }




}
