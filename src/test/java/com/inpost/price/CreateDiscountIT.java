package com.inpost.price;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inpost.price.model.Product;
import com.inpost.price.repository.AmountDiscountRepository;
import com.inpost.price.repository.PercentageDiscountRepository;
import com.inpost.price.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateDiscountIT {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private PercentageDiscountRepository percentageDiscountRepository;

    @Autowired
    private AmountDiscountRepository amountDiscountRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    void shouldCreatePercentageDiscount() throws Exception {
        //  given
        var discountName = "testPercentageDiscount";
        var productIds = repository.findAll().stream().map(Product::getId).toList();
        var percentageDiscountBody = validPercentageDiscountBody(discountName, productIds);
        var percentageDiscountCount = percentageDiscountRepository.count();

        //  when
        var response = createPercentageDiscount(percentageDiscountBody);

        //  then
        response.andExpect(status().isAccepted());

        repository.flush();
        var updatedProducts = repository.findAll();

        assertTrue(updatedProducts.stream()
                .allMatch(product -> product.getPercentageDiscounts()
                        .stream()
                        .anyMatch(percentageDiscount -> percentageDiscount.getDiscountName().equals(discountName)))
        );
        assertTrue(percentageDiscountRepository.count() > percentageDiscountCount);
    }

    @Test
    @Transactional
    void shouldCreateAmountDiscount() throws Exception {
        //  given
        var discountName = "testAmountDiscount";
        var productIds = repository.findAll().stream().map(Product::getId).toList();
        var amountDiscountBody = validAmountDiscountBody(discountName, productIds);
        var amountDiscountCount = amountDiscountRepository.count();

        //  when
        var response = createAmountDiscount(amountDiscountBody);

        //  then
        response.andExpect(status().isAccepted());

        repository.flush();
        var updatedProducts = repository.findAll();

        assertTrue(updatedProducts.stream()
                .allMatch(product -> product.getAmountDiscounts()
                        .stream()
                        .anyMatch(amountDiscount -> amountDiscount.getDiscountName().equals(discountName)))
        );
        assertTrue(amountDiscountRepository.count() > amountDiscountCount);
    }


    private ObjectNode validPercentageDiscountBody(String name, List<String> productIds) {
        var productIdsNode = objectMapper.createArrayNode();
        productIds.forEach(productIdsNode::add);
        return objectMapper.createObjectNode()
                .put("discountName", name)
                .put("discountPercentage", 10)
                .put("expirationDate", String.valueOf(LocalDateTime.now().plusDays(30)))
                .set("productIds", productIdsNode);

    }

    private ObjectNode validAmountDiscountBody(String name, List<String> productIds) {
        var productIdsNode = objectMapper.createArrayNode();
        productIds.forEach(productIdsNode::add);
        return objectMapper.createObjectNode()
                .put("discountName", name)
                .put("discountPercentage", 10)
                .put("minimumAmount", 10)
                .put("productAmountDiscountMultiplier", 50)
                .put("expirationDate", String.valueOf(LocalDateTime.now()))
                .set("productIds", productIdsNode);

    }

    private ResultActions createPercentageDiscount(ObjectNode body) throws Exception {
        return mockMvc.perform(
                post("/price/percentageDiscount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)));
    }

    private ResultActions createAmountDiscount(ObjectNode body) throws Exception {
        return mockMvc.perform(
                post("/price/amountDiscount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)));
    }

}
