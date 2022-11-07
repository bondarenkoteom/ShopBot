package com.shop.ShopBot.database.service;

import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.database.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteAllEditing() {
        productRepository.deleteByIsEditingTrue();
    }

    public Product getEditingProductByOwnerId(Long ownerId) {
        return productRepository.getProductByOwnerIdAndIsEditing(ownerId, true);
    }

    public List<Product> getAllProducts(Long ownerId) {
        return productRepository.getProductsByOwnerIdAndIsEditing(ownerId, false);
    }

    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAllProducts(pageable);
    }
}
