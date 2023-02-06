package com.shop.ShopBot.database.repository;

import com.shop.ShopBot.database.model.Dispute;
import com.shop.ShopBot.database.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DisputeRepository extends JpaRepository<Dispute, Long> {

    @Query(value = """
            SELECT * FROM t_dispute d
            WHERE d.purchase_id = :purchase_id
            ORDER BY DATE""", nativeQuery = true)
    List<Dispute> findDisputeMessages(@Param("purchase_id") Long purchaseId);
}
