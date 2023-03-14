package com.marketplace.database.repository;

import com.marketplace.constant.Category;
import com.marketplace.database.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.Optional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getProductById(Long id);

    void deleteByIsEditingTrueAndOwnerId(Long ownerId);

    Optional<Product> getProductByOwnerIdAndIsEditing(Long ownerId, boolean isEditing);

    Page<Product> getProductsByOwnerIdAndIsEditing(Long ownerId, boolean isEditing, Pageable pageable);

    @Query(value = "SELECT pull_element(:id)", nativeQuery = true)
    String pollItem(@Param("id") Long id);






    @Query("SELECT p FROM Product p")
    Page<Product> findAllProducts(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' AND p.category = :category AND p.ownerId = :ownerId")
    Page<Product> findProducts(@Param("category") Category category, @Param("ownerId") Long ownerId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' AND p.category = :category")
    Page<Product> findProducts(@Param("category") Category category, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' AND p.ownerId = :ownerId")
    Page<Product> findProducts(@Param("ownerId") Long ownerId, Pageable pageable);

    @Query(value = """
            SELECT * FROM t_product, to_tsquery(:text) query, ts_rank_cd(product_vector, query) rank
            WHERE query @@ product_vector AND status = 'ACTIVE' AND category = :category AND owner_id = :ownerId ORDER BY id""",
            countQuery = """
                    SELECT COUNT(*) FROM t_product, to_tsquery(:text) query, ts_rank_cd(product_vector, query) rank
                    WHERE query @@ product_vector AND status = 'ACTIVE' AND category = :category AND owner_id = :ownerId""",
            nativeQuery = true)
    Page<Product> fullTextSearch(@Param("text") String text, @Param("category") String category, @Param("ownerId") Long ownerId, Pageable pageable);

    @Query(value = """
            SELECT * FROM t_product, to_tsquery(:text) query, ts_rank_cd(product_vector, query) rank
            WHERE query @@ product_vector AND status = 'ACTIVE' AND category = :category ORDER BY id""",
            countQuery = """
                    SELECT COUNT(*) FROM t_product, to_tsquery(:text) query, ts_rank_cd(product_vector, query) rank
                    WHERE query @@ product_vector AND status = 'ACTIVE' AND category = :category""",
            nativeQuery = true)
    Page<Product> fullTextSearch(@Param("text") String text, @Param("category") String category, Pageable pageable);

    @Query(value = """
            SELECT * FROM t_product, to_tsquery(:text) query, ts_rank_cd(product_vector, query) rank
            WHERE query @@ product_vector AND status = 'ACTIVE' AND owner_id = :ownerId ORDER BY id""",
            countQuery = """
                    SELECT COUNT(*) FROM t_product, to_tsquery(:text) query, ts_rank_cd(product_vector, query) rank
                    WHERE query @@ product_vector AND status = 'ACTIVE' AND owner_id = :ownerId""",
            nativeQuery = true)
    Page<Product> fullTextSearch(@Param("text") String text, @Param("ownerId") Long ownerId, Pageable pageable);

    @Query(value = """
            SELECT * FROM t_product, to_tsquery(:text) query, ts_rank_cd(product_vector, query) rank
            WHERE query @@ product_vector AND status = 'ACTIVE' ORDER BY id""",
            countQuery = """
                    SELECT COUNT(*) FROM t_product, to_tsquery(:text) query, ts_rank_cd(product_vector, query) rank
                    WHERE query @@ product_vector AND status = 'ACTIVE'""",
            nativeQuery = true)
    Page<Product> fullTextSearch(@Param("text") String text, Pageable pageable);
}
