package com.shop.ShopBot.database.repository;

import com.shop.ShopBot.database.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("UPDATE Purchase p SET p.status = 'CONFIRMED', p.product.ratingGood = p.product.ratingGood + 1 WHERE p.date < :overdueDate AND p.status = 'IN_PROGRESS'")
    void updateOverdueOrders(@Param("overdueDate") Date date);
}
