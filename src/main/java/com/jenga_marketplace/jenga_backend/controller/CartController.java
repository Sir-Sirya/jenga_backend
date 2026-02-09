package com.jenga_marketplace.jenga_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // Resolves symbol conflicts
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jenga_marketplace.jenga_backend.model.CartItem;
import com.jenga_marketplace.jenga_backend.model.dto.CartRequest;
import com.jenga_marketplace.jenga_backend.service.CartService;

/**
 * CartController: Orchestrates Point of Sale (POS) operations for the Jenga Marketplace.
 * This controller acts as the API bridge between the React frontend and the MySQL 'cart_items' table.
 */
@RestController
@RequestMapping("/api/cart")
/* * CORS CONFIGURATION: 
 * Allows cross-origin requests from the Vite frontend (5173) and backend local tools (8080).
 */
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"}) 
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * ADD TO CART: Persists or updates a material selection in the database.
     * Uses the CartRequest DTO to securely transfer data from the client.
     * @param request Contains userId, productId, and desired quantity.
     * @return The updated or newly created CartItem object.
     */
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartRequest request) {
        // request.getUserId() now resolved by the imported DTO class
        CartItem item = cartService.addItemToCart(
            request.getUserId(), 
            request.getProductId(), 
            request.getQuantity()
        );
        return ResponseEntity.ok(item);
    }

    /**
     * RETRIEVE CART: Gets items for a specific buyer.
     * Fix: Corrected capitalization to @GetMapping to resolve IDE errors.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getUserCart(userId));
    }
    
    /**
     * REMOVE ITEM: Permanently deletes a material from the buyer's site list.
     * @param cartItemId The primary key of the entry to be removed from MySQL.
     * @return A 200 OK response upon successful deletion.
     */
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> removeItem(@PathVariable Long cartItemId) {
        // Fix: Calls deleteItem in CartService, resolving undefined method errors.
        cartService.deleteItem(cartItemId);
        return ResponseEntity.ok().build();
    }
}