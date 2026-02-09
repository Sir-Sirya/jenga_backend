package com.jenga_marketplace.jenga_backend.service;

import com.jenga_marketplace.jenga_backend.model.CartItem;
import com.jenga_marketplace.jenga_backend.model.Product;
import com.jenga_marketplace.jenga_backend.model.User;
import com.jenga_marketplace.jenga_backend.repository.CartRepository;
import com.jenga_marketplace.jenga_backend.repository.ProductRepository;
import com.jenga_marketplace.jenga_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * CartService: Business logic for managing the buyer's material list.
 * Aligns with the 'cart_items' table in the database schema.
 */
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Adds an item to the cart or increments quantity if it already exists.
     * Includes a security constraint to verify stock availability.
     */
    @Transactional
    public CartItem addItemToCart(Long userId, Long productId, Integer quantity) {
        // 1. Validate Product and Stock
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock in hardware store");
        }

        // 2. Check for existing item in this user's cart
        Optional<CartItem> existing = cartRepository.findByUserIdAndProductId(userId, productId);

        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + quantity);
            return cartRepository.save(item);
        } else {
            // 3. Create new entry for first-time material selection
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Note: Ensure your CartItem model has a constructor for (User, Product, Integer)
            CartItem newItem = new CartItem(user, product, quantity);
            return cartRepository.save(newItem);
        }
    }

    /**
     * Retrieves all materials currently listed in the user's cart.
     */
    public List<CartItem> getUserCart(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    /**
     * Removes an item from the cart table.
     * This resolves the "method undefined" error in the CartController.
     */
    @Transactional
    public void deleteItem(Long cartItemId) {
        if (!cartRepository.existsById(cartItemId)) {
            throw new RuntimeException("Cart item not found");
        }
        cartRepository.deleteById(cartItemId);
    }
}