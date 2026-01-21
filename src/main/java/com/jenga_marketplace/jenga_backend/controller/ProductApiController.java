package com.jenga_marketplace.jenga_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; // Fixes 'List' cannot be resolved
import org.springframework.web.bind.annotation.RestController;

import com.jenga_marketplace.jenga_backend.model.Product;
import com.jenga_marketplace.jenga_backend.model.dto.ProductRequest;
import com.jenga_marketplace.jenga_backend.repository.CategoryRepository;
import com.jenga_marketplace.jenga_backend.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
// Allow both ports to prevent CORS blocks
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081"}) 
public class ProductApiController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // 1. GET: Fetch all products for the homepage
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. POST: Receive new product from the Seller Form
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request) {
        try {
            Product product = new Product();
            product.setTitle(request.getTitle());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            product.setImageUrl(request.getImageUrl());
            product.setStockQuantity(request.getStockQuantity());

            // Link to the subcategory (e.g., Paints, Timber, Doors)
            if (request.getCategoryId() != null) {
                categoryRepository.findById(request.getCategoryId())
                    .ifPresent(product::setCategory);
            }

            productRepository.save(product);
            return ResponseEntity.ok("Product successfully added to Jenga Marketplace!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating product: " + e.getMessage());
        }
    }
}