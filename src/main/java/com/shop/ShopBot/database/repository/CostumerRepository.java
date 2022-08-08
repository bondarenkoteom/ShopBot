package com.shop.ShopBot.database.repository;

import com.shop.ShopBot.database.entity.CostumerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostumerRepository extends JpaRepository<CostumerEntity, Long> {

    Long deleteByName(String name);

}
