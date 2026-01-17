package com.jenga_marketplace.jenga_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jenga_marketplace.jenga_backend.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // JpaRepository gives us findAll(), findById(), save(), and delete() automatically!
}
