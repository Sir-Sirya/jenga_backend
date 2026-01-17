package com.jenga_marketplace.jenga_backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, length = 100)
    private String slug;

    // --- NEW UI ENHANCEMENT FIELDS ---

    @Column(name = "icon_name", length = 100)
    private String iconName; // Stores Lucide icon names like 'hammer' or 'zap'

    @Column(name = "image_url")
    private String imageUrl; // High-quality Unsplash links

    @Column(name = "is_featured")
    private boolean featured; // Helps highlight top categories on the homepage

    // --- RELATIONSHIPS ---

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("category") // Prevents the API from crashing during JSON conversion
    private List<Product> products;
}