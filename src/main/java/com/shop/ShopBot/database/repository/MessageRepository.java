package com.shop.ShopBot.database.repository;

import com.shop.ShopBot.database.model.Message;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.database.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = """
            SELECT * FROM t_message m
            WHERE m.sender_id IN (:super_id, :id) AND m.receiver_id IN (:super_id, :id)
            ORDER BY DATE""",
            countQuery = "SELECT COUNT(*) FROM t_message m WHERE m.sender_id IN (:super_id, :id) AND m.receiver_id IN (:super_id, :id)",
            nativeQuery = true)
    Page<Message> findChatMessages(@Param("super_id") Long superId, @Param("id") Long id, Pageable pageable);

}
