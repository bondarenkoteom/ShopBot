package com.shop.ShopBot.database.service;

import com.shop.ShopBot.database.entity.CostumerEntity;
import com.shop.ShopBot.database.repository.CostumerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CostumerService {

    private final CostumerRepository costumerRepository;

    public CostumerService(CostumerRepository costumerRepository) {
        this.costumerRepository = costumerRepository;
    }


    public List<CostumerEntity> findAllCostumer() {
        return costumerRepository.findAll();
    }


    public Optional<CostumerEntity> findById(Long id) {
        return costumerRepository.findById(id);
    }


    public CostumerEntity saveCostumer(CostumerEntity costumerEntity) {
        return costumerRepository.save(costumerEntity);
    }

    public CostumerEntity updateCostumer(CostumerEntity costumerEntity) {
        return costumerRepository.save(costumerEntity);
    }

    public void deleteCostumer(Long id) {
        costumerRepository.deleteById(id);
    }

}
