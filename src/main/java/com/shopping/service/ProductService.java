package com.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shopping.entity.Product;
import com.shopping.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> searchAndPaginate(String keyword, int page, int size) {
        int safePage = Math.max(page, 1);
        return productRepository.findByProductNameContainingIgnoreCase(keyword.trim(), PageRequest.of(safePage - 1, size));
    }

    public void save(Product product) throws Exception {
        String productName = product.getProductName() == null ? "" : product.getProductName().trim();
        product.setProductName(productName);

        if (productName.isBlank()) {
            throw new Exception("Tên sản phẩm không được để trống!");
        }
        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new Exception("Giá sản phẩm không hợp lệ!");
        }
        if (product.getCategory() == null || product.getCategory().getId() == 0) {
            throw new Exception("Vui lòng chọn danh mục!");
        }

        productRepository.save(product);
    }

    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public void delete(int id) {
        productRepository.deleteById(id);
    }
}