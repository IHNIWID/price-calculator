package com.inpost.price;


import com.inpost.price.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest
public class CalculatePriceIT {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCalculatePriceWithoutAnyDiscounts() throws Exception {
        //  given
        var productId = "8a4b4a75-c562-45f3-97c0-7a99051cc78f";
        var amount = 10;
        var product = repository.findById(productId).orElseThrow();

        //  when
        var response = calculatePrice(productId, amount);

        //  then
                response.andExpect(status().isOk())
                .andExpect(jsonPath("productId").isNotEmpty())
                .andExpect(jsonPath("productId").value(productId))
                .andExpect(jsonPath("productName").isNotEmpty())
                .andExpect(jsonPath("productName").value(product.getProductName()))
                .andExpect(jsonPath("oneProductPrice").isNotEmpty())
                .andExpect(jsonPath("oneProductPrice").value(product.getPrice()))
                .andExpect(jsonPath("overallPrice").isNotEmpty())
                .andExpect(jsonPath("overallPrice").value(new BigDecimal("1899.90")))
                .andExpect(jsonPath("amount").isNotEmpty())
                .andExpect(jsonPath("amount").value(amount))
        ;
    }

    @Test
    void shouldCalculatePriceWithPercentageDiscount() throws Exception {
        //  given
        var productId = "8dfa6398-a1a4-4c95-9d9e-54ba3db7c550";
        var amount = 3;
        var product = repository.findById(productId).orElseThrow();

        //  when
        var response = calculatePrice(productId, amount);

        //  then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("productId").isNotEmpty())
                .andExpect(jsonPath("productId").value(productId))
                .andExpect(jsonPath("productName").isNotEmpty())
                .andExpect(jsonPath("productName").value(product.getProductName()))
                .andExpect(jsonPath("oneProductPrice").isNotEmpty())
                .andExpect(jsonPath("oneProductPrice").value(product.getPrice()))
                .andExpect(jsonPath("overallPrice").isNotEmpty())
                .andExpect(jsonPath("overallPrice").value(new BigDecimal("5132.38")))
                .andExpect(jsonPath("amount").isNotEmpty())
                .andExpect(jsonPath("amount").value(amount))
        ;
    }

    @Test
    void shouldCalculatePriceWithAmountDiscount() throws Exception {
        //  given
        var productId = "9e679843-3a5e-4cca-b159-f4ec3846f8f1";
        var amount = 61;
        var product = repository.findById(productId).orElseThrow();

        //  when
        var response = calculatePrice(productId, amount);

        //  then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("productId").isNotEmpty())
                .andExpect(jsonPath("productId").value(productId))
                .andExpect(jsonPath("productName").isNotEmpty())
                .andExpect(jsonPath("productName").value(product.getProductName()))
                .andExpect(jsonPath("oneProductPrice").isNotEmpty())
                .andExpect(jsonPath("oneProductPrice").value(product.getPrice()))
                .andExpect(jsonPath("overallPrice").isNotEmpty())
                .andExpect(jsonPath("overallPrice").value(new BigDecimal("42.27")))
                .andExpect(jsonPath("amount").isNotEmpty())
                .andExpect(jsonPath("amount").value(amount))
        ;
    }

    @Test
    void shouldCalculatePriceWithAmountAndPercentageDiscount() throws Exception {
        //  given
        var productId = "4a7d3a63-21c5-4372-a22c-a503a99b673e";
        var amount = 61;
        var product = repository.findById(productId).orElseThrow();

        //  when
        var response = calculatePrice(productId, amount);

        //  then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("productId").isNotEmpty())
                .andExpect(jsonPath("productId").value(productId))
                .andExpect(jsonPath("productName").isNotEmpty())
                .andExpect(jsonPath("productName").value(product.getProductName()))
                .andExpect(jsonPath("oneProductPrice").isNotEmpty())
                .andExpect(jsonPath("oneProductPrice").value(product.getPrice()))
                .andExpect(jsonPath("overallPrice").isNotEmpty())
                .andExpect(jsonPath("overallPrice").value(new BigDecimal("3843.00")))
                .andExpect(jsonPath("amount").isNotEmpty())
                .andExpect(jsonPath("amount").value(amount))
        ;
    }

    private ResultActions calculatePrice(String id, Integer amount) throws Exception {
        return mockMvc.perform(
                get("/price")
                        .queryParam("id", id)
                        .queryParam("amount", String.valueOf(amount))
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }

}
