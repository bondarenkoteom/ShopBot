package com.marketplace.database.service;

import com.marketplace.constant.Category;
import com.marketplace.database.model.Product;
import com.marketplace.database.repository.jpa.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Product> getAllProducts(String productName, Pageable pageable) {
        return productRepository.getProductsByProductNameAndIsEditing(productName, false, pageable);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAllProducts(pageable);
    }

    public Page<Product> findById(Long id, Pageable pageable) {
        return productRepository.findById(id, pageable);
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> findAllProducts(String text, Category category, Long ownerId, Pageable pageable) {
        Pageable simpleSearchPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("price").descending());

        if (text != null && text.isEmpty() && category != null && ownerId != null) {
            return category.equals(Category.ALL) ?
                    productRepository.fullTextSearch(text, ownerId, pageable) :
                    productRepository.fullTextSearch(text, category.name(), ownerId, pageable);
        } else if (text != null && text.isEmpty() && category != null) {
            return category.equals(Category.ALL) ?
                    productRepository.fullTextSearch(text, pageable) :
                    productRepository.fullTextSearch(text, category.name(), pageable);
        } else if (text != null && text.isEmpty() && ownerId != null) {
            return productRepository.fullTextSearch(text, ownerId, pageable);
        } else if (category != null && ownerId != null) {
            return category.equals(Category.ALL) ?
                    productRepository.findProducts(ownerId, simpleSearchPageable) :
                    productRepository.findProducts(category, ownerId, simpleSearchPageable);
        } else if (category != null) {
            return category.equals(Category.ALL) ?
                    productRepository.findAllProducts(simpleSearchPageable) :
                    productRepository.findProducts(category, simpleSearchPageable);
        } else if (ownerId != null) {
            return productRepository.findProducts(ownerId, simpleSearchPageable);
        } else {
            return productRepository.findAllProducts(simpleSearchPageable);
        }
    }

    public String pollItem(Long id) {
        return productRepository.pollItem(id);
    }
}
