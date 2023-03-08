package com.marketplace.database.repository;

import com.marketplace.database.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getProductById(Long id);

    void deleteByIsEditingTrue();

    Product getProductByOwnerIdAndIsEditing(Long ownerId, boolean isEditing);

    Page<Product> getProductsByOwnerIdAndIsEditing(Long ownerId, boolean isEditing, Pageable pageable);

    @Query("SELECT p FROM Product p")
    LinkedList<Product> findAllProducts(Pageable pageable);

    @Query(value = "SELECT pull_element(:id)", nativeQuery = true)
    String pollItem(@Param("id") Long id);
}
