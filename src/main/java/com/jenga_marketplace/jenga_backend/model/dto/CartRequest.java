package com.jenga_marketplace.jenga_backend.model.dto;

import lombok.Data;

/**
 * CartRequest: The DTO used to receive 'AddToCart' payloads from React.
 * Resolves 'Cannot Find Symbol' errors by providing public getters.
 */
@Data // Generates Getters and Setters automatically
public class CartRequest {
    private Long userId;    // Matches BIGINT 'user_id'
    private Long productId; // Matches BIGINT 'product_id'
    private Integer quantity;
}
/*
 * CartRequest: Public DTO for incoming JSON payloads.
 * @Data automatically generates getUserId(), getProductId(), etc..
 */