package com.jenga_marketplace.jenga_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jenga_marketplace.jenga_backend.model.Product; // Fixes "List cannot be resolved"
import com.jenga_marketplace.jenga_backend.repository.ProductRepository;
    @RestController
    @RequestMapping("/api/products")
    @CrossOrigin(origins = "http://localhost:5173") // This allows the "Donor" React app to talk to us
  
public class ProductApiController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        // This fetches from MySQL and automatically converts it to JSON
        return productRepository.findAll();
    }
}
    

