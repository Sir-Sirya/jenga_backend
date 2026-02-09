package com.jenga_marketplace.jenga_backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CartItem: Maps the bridge between Users and Products in the 'cart_items' table.
 */
@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor // Required for JPA hydration
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;

    @Column(name = "added_at")
    private LocalDateTime addedAt = LocalDateTime.now();

    /**
     * CONSTRUCTOR FIX: Explicitly defined to resolve the 'No Suitable Constructor' error.
     */
    public CartItem(User user, Product product, Integer quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.addedAt = LocalDateTime.now();
    }
}