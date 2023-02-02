package com.shop.ShopBot.database.service;

import com.shop.ShopBot.constant.OrderStatus;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.database.model.Purchase;
import com.shop.ShopBot.database.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    public Page<Purchase> getAllPurchases(Long buyerId, Pageable pageable) {
        return purchaseRepository.getPurchasesByBuyerId(buyerId, pageable);
    }

    public void createOrder(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    public void updateOrderStatus(Long purchaseId, OrderStatus status) {
        Purchase purchase = purchaseRepository.getReferenceById(purchaseId);
        purchase.setStatus(status);
        purchaseRepository.save(purchase);
    }
    @Scheduled(cron = "0 0 10 * * *")
    private void updateOrdersStatus() {
        System.out.println("!!!!!!!!!!! UPDATE OVERDUE ORDERS !!!!!!!!!!!");
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(1);
        purchaseRepository.updateOverdueOrders(Date.from(localDateTime.toInstant(ZoneOffset.UTC)));
    }

    public Optional<Purchase> getById(Long id) {
        return purchaseRepository.findById(id);
    }
}
