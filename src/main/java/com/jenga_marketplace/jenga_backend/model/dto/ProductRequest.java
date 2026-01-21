package com.jenga_marketplace.jenga_backend.model.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductRequest {
    private String title;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Integer stockQuantity;
    private Integer categoryId; // This tells Jenga which subcategory to link to
    private Long sellerId;      // This links the product to the user
}