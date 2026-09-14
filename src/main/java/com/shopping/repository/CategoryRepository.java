package com.shopping.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Page<Category> findByCategoryNameContainingIgnoreCase(String keyword, Pageable pageable);
    boolean existsByCategoryName(String categoryName);
    Optional<Category> findByCategoryNameIgnoreCase(String categoryName);
}