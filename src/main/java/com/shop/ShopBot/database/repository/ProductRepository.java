package com.shop.ShopBot.database.repository;

import com.shop.ShopBot.database.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getProductById(Long id);

    void deleteByIsEditingTrue();

    Product getProductByOwnerIdAndIsEditing(Long ownerId, boolean isEditing);

    List<Product> getProductsByOwnerIdAndIsEditing(Long ownerId, boolean isEditing);

    @Query("SELECT p FROM Product p")
    LinkedList<Product> findAllProducts(Pageable pageable);

    @Query(value = "SELECT pull_element(:id)", nativeQuery = true)
    String pollItem(@Param("id") Long id);
}
