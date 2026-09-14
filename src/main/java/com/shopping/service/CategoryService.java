package com.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shopping.entity.Category;
import com.shopping.repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> searchAndPaginate(String keyword, int page, int size) {
        int safePage = Math.max(page, 1);
        return categoryRepository.findByCategoryNameContainingIgnoreCase(keyword.trim(), PageRequest.of(safePage - 1, size));
    }

    public void save(Category category) throws Exception {
        String normalizedName = category.getCategoryName() == null ? "" : category.getCategoryName().trim();
        category.setCategoryName(normalizedName);

        if (normalizedName.isEmpty()) {
            throw new Exception("Tên danh mục không được để trống!");
        }

        var existing = categoryRepository.findByCategoryNameIgnoreCase(normalizedName).orElse(null);
        if (existing != null && existing.getId() != category.getId()) {
            throw new Exception("Tên danh mục đã tồn tại!");
        }

        categoryRepository.save(category);
    }

    public Category findById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void delete(int id) {
        categoryRepository.deleteById(id);
    }
}