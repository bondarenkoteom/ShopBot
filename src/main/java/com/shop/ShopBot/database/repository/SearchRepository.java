package com.shop.ShopBot.database.repository;

import com.shop.ShopBot.database.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface SearchRepository extends CrudRepository<Product, Long> {

    @Query(value = "SELECT * FROM t_product, to_tsquery(:text) query, ts_rank_cd(product_vector, query) rank WHERE query @@ product_vector AND status = 'ACTIVE'",
            nativeQuery = true)
    LinkedList<Product> fullTextSearch(Pageable pageable, @Param("text") String text);
}
