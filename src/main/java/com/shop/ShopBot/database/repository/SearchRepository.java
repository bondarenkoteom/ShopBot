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

    @Query("SELECT p FROM Product p WHERE fts(:text) = true")
    LinkedList<Product> fullTextSearch(Pageable pageable, @Param("text") String text);
}
