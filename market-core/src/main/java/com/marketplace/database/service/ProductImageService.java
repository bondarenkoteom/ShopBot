package com.marketplace.database.service;

import com.marketplace.constant.Category;
import com.marketplace.database.model.Product;
import com.marketplace.database.model.ProductImage;
import com.marketplace.database.repository.ProductImageRepository;
import com.marketplace.database.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

//    public ProductImage save(ProductImage productImage) {
//        return productImageRepository.save(productImage);
//    }

}
