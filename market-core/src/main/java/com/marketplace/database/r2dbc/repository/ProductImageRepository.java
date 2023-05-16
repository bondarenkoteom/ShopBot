package com.marketplace.database.r2dbc.repository;

import com.marketplace.database.r2dbc.model.ProductImage;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends R2dbcRepository<ProductImage, Long> {

}
