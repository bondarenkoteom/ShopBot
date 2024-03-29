package com.marketplace.database.jpa.repository;

import com.marketplace.constant.OrderStatus;
import com.marketplace.database.jpa.model.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Modifying
    @Query(value = "CALL update_overdue_orders(:overdue_date);", nativeQuery = true)
    void updateOverdueOrders(@Param("overdue_date") Date date);

    @Modifying
    @Query(value = "CALL confirm_order(:order_id);", nativeQuery = true)
    void confirmDelivery(@Param("order_id") Long purchaseId);

    @Modifying
    @Query(value = "CALL decline_order(:order_id);", nativeQuery = true)
    void declineDispute(@Param("order_id") Long purchaseId);

    Page<Purchase> findByBuyerId(Long buyerId, Pageable pageable);

    Page<Purchase> findById(Long id, Pageable pageable);

//    @Query(value = "SELECT * from t_purchase WHERE status = 'DISPUTE' AND buyer_id = :id", nativeQuery = true)
    List<Purchase> findPurchasesByBuyerIdAndStatus(Long buyerId, OrderStatus status);

//    @Query(value = "SELECT * from t_purchase WHERE status = 'DISPUTE' AND seller_id = :id", nativeQuery = true)
    List<Purchase> findPurchasesBySellerIdAndStatus(Long buyerId, OrderStatus status);
}
