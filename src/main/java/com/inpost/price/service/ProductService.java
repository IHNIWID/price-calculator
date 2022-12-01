package com.inpost.price.service;

import com.inpost.price.repository.ProductRepository;
import com.inpost.price.service.model.ProductDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Set<ProductDto> getProducts(){
        return productRepository.findAll()
                .stream()
                .map(ProductDto::from)
                .collect(Collectors.toSet());
    }

}
