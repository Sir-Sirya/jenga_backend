package com.jenga_marketplace.jenga_backend.controller;

// These imports are the 'tools' this file needs to function
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jenga_marketplace.jenga_backend.model.Category;
import com.jenga_marketplace.jenga_backend.repository.CategoryRepository;

/**
 * @RestController: Tells Spring this file handles web requests (JSON).
 * @RequestMapping: Every URL in this file will start with /api/categories.
 * @CrossOrigin: Permits your React app (Port 8081 or 5173) to talk to this backend.
 */
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = {"http://localhost:8081", "http://localhost:5173"}) 
public class CategoryController {

    // @Autowired automatically connects this controller to the database bridge
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * GET http://localhost:8080/api/categories
     * Returns a flat list of EVERY category in your database.
     */
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * GET http://localhost:8080/api/categories/hierarchy
     * This is the 'Brain' of your Mega-Menu!
     * It only returns categories that have NO parent. 
     * Because of the @JsonIgnoreProperties in your Entity, Jackson will 
     * automatically nest the sub-categories inside these parents.
     */
    @GetMapping("/hierarchy")
    public List<Category> getCategoryHierarchy() {
        // This will now work because we defined it in the Repository above
        return categoryRepository.findByParentIsNull(); 
    }
}