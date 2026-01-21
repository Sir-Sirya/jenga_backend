package com.jenga_marketplace.jenga_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jenga_marketplace.jenga_backend.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // This interface gives you all the tools to talk to your MySQL categories table
}