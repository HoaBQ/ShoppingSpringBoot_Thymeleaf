package com.shopping.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByProductNameContainingIgnoreCase(String keyword, Pageable pageable);
    Optional<Product> findByProductNameIgnoreCase(String productName);
    boolean existsByProductNameIgnoreCase(String productName);
}