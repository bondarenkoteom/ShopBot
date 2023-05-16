package com.marketplace.database.jpa.repository;

import com.marketplace.database.jpa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query(value = """
            SELECT * from t_user WHERE id IN (
            SELECT m.receiver_id FROM t_message m WHERE m.sender_id = :id
            UNION
            SELECT m.sender_id FROM t_message m WHERE m.receiver_id = :id)""", nativeQuery = true)
    List<User> unionChatsUsers(@Param("id") Long id);

    @Query(value = "SELECT * FROM t_user ORDER BY sells ASC, rating ASC", nativeQuery = true)
    List<User> findTop25Vendors();

    Page<User> findAll(Pageable pageable);

    Page<User> findByIdIn(List<Long> userIds, Pageable pageable);

    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
}
