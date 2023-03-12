package com.marketplace.database.service;

import com.marketplace.constant.Category;
import com.marketplace.database.model.Product;
import com.marketplace.database.repository.ProductRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteAllEditing(Long ownerId) {
        productRepository.deleteByIsEditingTrueAndOwnerId(ownerId);
    }

    public Optional<Product> getEditingProductByOwnerId(Long ownerId) {
        return productRepository.getProductByOwnerIdAndIsEditing(ownerId, true);
    }

    public Page<Product> getAllProducts(Long ownerId, Pageable pageable) {
        return productRepository.getProductsByOwnerIdAndIsEditing(ownerId, false, pageable);
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> findAllProducts(String text, Category category, Long ownerId, Pageable pageable) {
        if (text != null && text.isEmpty() && category != null && ownerId != null) {
            return productRepository.fullTextSearch(text, category.name(), ownerId, pageable);
        } else if (text != null && text.isEmpty() && category != null) {
            return productRepository.fullTextSearch(text, category.name(), pageable);
        } else if (text != null && text.isEmpty() && ownerId != null) {
            return productRepository.fullTextSearch(text, ownerId, pageable);
        } else if (category != null && ownerId != null) {
            return productRepository.findProducts(category.name(), ownerId, pageable);
        } else if (category != null) {
            return productRepository.findProducts(category.name(), pageable);
        } else if (ownerId != null) {
            return productRepository.findProducts(ownerId, pageable);
        } else {
            return productRepository.findAllProducts(pageable);
        }
    }

    public String pollItem(Long id) {
        return productRepository.pollItem(id);
    }
}
