package com.marketplace.database.repository.r2dbc;

import com.marketplace.database.model.ProductImage;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends R2dbcRepository<ProductImage, Long> {

}
