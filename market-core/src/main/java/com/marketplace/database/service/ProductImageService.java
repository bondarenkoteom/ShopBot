package com.marketplace.database.service;

import com.marketplace.database.model.ProductImage;
import com.marketplace.database.repository.r2dbc.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    public Mono<ProductImage> save(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    public Mono<ProductImage> findById(Long id) {
        return productImageRepository.findById(id);
    }

}
