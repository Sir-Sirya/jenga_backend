package com.jenga_marketplace.jenga_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jenga_marketplace.jenga_backend.model.Category;
/**
 * The Repository is the 'Bridge' to your MySQL Database.
 * JpaRepository provides basic tools like save, delete, and find.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // This interface gives you all the tools to talk to your MySQL categories table
/**
     * CUSTOM QUERY: findByParentIsNull
     * This tells Spring Data JPA to generate a SQL query: 
     * "SELECT * FROM categories WHERE parent_id IS NULL"
     * This is essential for getting 'Root' categories like Timber or Ceramics.
     */
    List<Category> findByParentIsNull();
}