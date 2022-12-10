package com.shop.ShopBot.database.service;

import com.shop.ShopBot.constant.OrderStatus;
import com.shop.ShopBot.database.model.Purchase;
import com.shop.ShopBot.database.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    public void createOrder(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    public void updateOrderStatus(Long purchaseId, OrderStatus status) {
        Purchase purchase = purchaseRepository.getReferenceById(purchaseId);
        purchase.setStatus(status);
        purchaseRepository.save(purchase);
    }

    public void updateOverdueOrders() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(1);
        purchaseRepository.updateOverdueOrders(Date.from(localDateTime.toInstant(ZoneOffset.UTC)));
    }
}
