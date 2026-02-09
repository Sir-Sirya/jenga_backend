package com.jenga_marketplace.jenga_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jenga_marketplace.jenga_backend.model.CartItem;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    // Matches 'user_id' column in MySQL
    List<CartItem> findByUserId(Long userId);

    // Prevents duplicate rows for the same product per user
    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);
}