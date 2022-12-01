package com.inpost.price.api;

import com.inpost.price.service.ProductService;
import com.inpost.price.service.model.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @GetMapping(path = "/list")
    public ResponseEntity<Set<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

}
