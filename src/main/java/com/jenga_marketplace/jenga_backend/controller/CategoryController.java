package com.jenga_marketplace.jenga_backend.controller;

// These imports are the "Map" that tells the Controller where to find your files
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping; // This fixes the 'List' cannot be resolved error
import org.springframework.web.bind.annotation.RestController;

import com.jenga_marketplace.jenga_backend.model.Category;
import com.jenga_marketplace.jenga_backend.repository.CategoryRepository;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:8081") 
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}