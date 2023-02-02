package com.shop.ShopBot.database.service;

import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.database.repository.ProductRepository;
import com.shop.ShopBot.database.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SearchRepository searchRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteAllEditing() {
        productRepository.deleteByIsEditingTrue();
    }

    public Product getEditingProductByOwnerId(Long ownerId) {
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

    public Page<Product> findAllProducts(Pageable pageable) {
        return searchRepository.findAllProducts(pageable);
    }

    public Page<Product> fullTextSearch(String text, Pageable pageable) {
        return searchRepository.fullTextSearch(text, pageable);
    }

    public String pollItem(Long id) {
        return productRepository.pollItem(id);
    }
}
