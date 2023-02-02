package com.shop.ShopBot.database.repository;

import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.database.model.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query(value = "UPDATE t_purchase p JOIN t_product pr ON p.product_id = pr.id SET p.status = 'CONFIRMED', pr.ratingGood = pr.ratingGood + 1 WHERE p.date < :overdueDate AND p.status = 'IN_PROGRESS'", nativeQuery = true)
    void updateOverdueOrders(@Param("overdueDate") Date date);

    Page<Purchase> getPurchasesByBuyerId(Long buyerId, Pageable pageable);
}
