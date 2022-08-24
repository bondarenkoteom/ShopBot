package com.shop.ShopBot.database.service;

import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.database.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

}
