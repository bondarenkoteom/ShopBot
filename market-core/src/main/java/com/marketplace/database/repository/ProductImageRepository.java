package com.marketplace.database.repository;

import com.marketplace.database.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
