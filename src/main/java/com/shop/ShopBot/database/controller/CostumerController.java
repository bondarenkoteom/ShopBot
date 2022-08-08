package com.shop.ShopBot.database.controller;

import com.shop.ShopBot.database.entity.CostumerEntity;
import com.shop.ShopBot.database.repository.CostumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequestMapping("/costumer")
public class CostumerController {

    @Autowired
    private CostumerRepository costumerService;

    @GetMapping
    public List<CostumerEntity> findAllCostumer() {
        return costumerService.findAll();
    }

    @PostMapping
    public CostumerEntity saveCostumer(@RequestBody CostumerEntity costumerEntity) {
        return costumerService.save(costumerEntity);
    }

    @DeleteMapping("/{name}")
    public void deleteCostumer(@PathVariable("name") String name) {
        costumerService.deleteByName(name);
    }

}
