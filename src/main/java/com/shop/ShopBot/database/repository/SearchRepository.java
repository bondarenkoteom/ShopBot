package com.shop.ShopBot.database.repository;

import com.shop.ShopBot.database.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends CrudRepository<Product, Long> {

    @Query(value = "SELECT * FROM t_product, to_tsquery(:text) query, ts_rank_cd(product_vector, query) rank WHERE query @@ product_vector AND status = 'ACTIVE' ORDER BY id",
            countQuery = "SELECT COUNT(*) FROM t_product, to_tsquery(:text) query, ts_rank_cd(product_vector, query) rank WHERE query @@ product_vector AND status = 'ACTIVE'",
            nativeQuery = true)
    Page<Product> fullTextSearch(@Param("text") String text, Pageable pageable);
}
